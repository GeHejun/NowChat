package com.ghj.web.service.impl;

import com.ghj.common.dto.response.FriendGroupResponse;
import com.ghj.common.dto.response.FriendResponse;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.web.service.MainFrameService;
import com.ghj.web.service.RestService;
import com.ghj.web.vo.GroupVO;
import com.ghj.web.vo.MainFrameVO;
import com.ghj.web.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gehj
 * @version 1.0
 * @description 主界面查询service
 * @date 2019/8/22 10:42
 */
@Service
public class MainFrameServiceImpl implements MainFrameService {

    @Resource
    RestService restService;

    @Override
    public MainFrameVO initMainFrame(Integer id) {
        //登录用户信息
        UserResponse userResponse = restService.queryUser(id).getData();
        UserVO userVO = UserVO.builder()
                .avatar(userResponse.getHeadPortrait())
                .id(userResponse.getId().toString())
                .status(restService.queryUserStateById(userResponse.getUserStateId()).getData().getName())
                .username(userResponse.getNickName())
                .build();
        //查询朋友列表
        List<FriendResponse> friendResponseList = restService.queryFriendList(id).getData();
        //根据组分组
        Map<Integer, List<FriendResponse>> groupMap = friendResponseList.stream()
                .collect(Collectors.groupingBy(FriendResponse::getFriendGroupId));
        List<GroupVO> groupVOList = new ArrayList<>(groupMap.keySet().size());
        groupMap.forEach((k, v) -> {
            //查询组信息
            FriendGroupResponse friendGroupResponse = restService.queryGroupById(k).getData();
            List<UserVO> friendVOList = new ArrayList<>(v.size());
            v.forEach(friendResponse -> {
                //查询朋友信息
                UserResponse friend = restService.queryUser(friendResponse.getFriendId()).getData();
                UserVO friendVO = UserVO.builder()
                        .avatar(friend.getHeadPortrait())
                        .id(friend.getId().toString())
                        .status(restService.queryUserStateById(friend.getUserStateId()).getData().getName())
                        .username(friend.getNickName())
                        .build();
                friendVOList.add(friendVO);
            });
            GroupVO groupVO = GroupVO.builder().id(friendGroupResponse.getId()).groupname(friendGroupResponse.getName()).list(friendVOList).build();
            groupVOList.add(groupVO);
        });
        //组装数据
        MainFrameVO mainFrameVO = MainFrameVO.builder().mine(userVO).friend(groupVOList).build();
        return mainFrameVO;
    }
}

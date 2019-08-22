package com.ghj.web.service.impl;

import com.ghj.common.dto.response.FriendGroupResponse;
import com.ghj.common.dto.response.FriendResponse;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.web.service.*;
import com.ghj.web.vo.FriendVO;
import com.ghj.web.vo.GroupVO;
import com.ghj.web.vo.MainFrameVO;
import com.ghj.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @Autowired
    FriendGroupService friendGroupService;

    @Autowired
    UserStateService userStateService;

    @Override
    public MainFrameVO initMainFrame(Integer id) {
        UserResponse userResponse = userService.queryUser(id).getData();
        UserVO userVO = UserVO.builder()
                .avatar(userResponse.getHeadPortrait())
                .id(userResponse.getId().toString())
                .status(userStateService.queryUserStateById(id).getData().getName())
                .username(userResponse.getNickName()).build();
        List<FriendResponse> friendResponseList = friendService.queryFriendList(id).getData();
        Map<Integer, List<FriendResponse>> groupMap = friendResponseList.stream().collect(Collectors.groupingBy(FriendResponse::getFriendGroupId));
        List<GroupVO> groupVOList = new ArrayList<>(groupMap.keySet().size());
        groupMap.forEach((k, v) -> {
            FriendGroupResponse friendGroupResponse = friendGroupService.queryGroupById(k).getData();
            List<FriendVO> friendVOList = new ArrayList<>(v.size());
            v.forEach(friendResponse -> {
                FriendVO friendVO = FriendVO.builder().build();
                friendVOList.add(friendVO);
            });
            GroupVO groupVO = GroupVO.builder().id(friendGroupResponse.getId()).groupname(friendGroupResponse.getName()).friend(friendVOList).build();
            groupVOList.add(groupVO);
        });
        MainFrameVO mainFrameVO = MainFrameVO.builder().mine(userVO).groupVOList(groupVOList).build();
        return mainFrameVO;
    }
}

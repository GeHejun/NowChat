package com.ghj.web.service.impl;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.*;
import com.ghj.web.service.MainFrameService;
import com.ghj.web.service.RestService;
import com.ghj.web.vo.*;
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
        UserVO userVO = buildUserVO(userResponse);
        //查询朋友列表
        List<FriendResponse> friendResponseList = restService.queryFriendList(id).getData();
        //根据组分组
        Map<Integer, List<FriendResponse>> groupMap = friendResponseList.stream()
                .collect(Collectors.groupingBy(FriendResponse::getFriendGroupId));
        List<FriendGroupVO> friendGroupVOList = new ArrayList<>(groupMap.keySet().size());
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
                        .username(friendResponse.getName())
                        .sign(friend.getSignature())
                        .build();
                friendVOList.add(friendVO);
            });
            FriendGroupVO friendGroupVO = FriendGroupVO.builder().id(friendGroupResponse.getId()).groupname(friendGroupResponse.getName()).list(friendVOList).build();
            friendGroupVOList.add(friendGroupVO);
        });
        List<GroupToUserResponse> groupToUserResponseList = restService.findGroupsByUserId(id).getData();
        List<GroupVO> groupVOList = new ArrayList<>(groupToUserResponseList.size());
        groupToUserResponseList.forEach(groupToUserResponse -> {
            UserGroupResponse userGroupResponse = restService.findGroupById(groupToUserResponse.getGroupId()).getData();
            GroupVO groupVO = GroupVO.builder().avatar(userGroupResponse.getIcon()).groupname(userGroupResponse.getName())
                    .id(userGroupResponse.getId().toString()).build();
            groupVOList.add(groupVO);
        });
        //组装数据
        MainFrameVO mainFrameVO = MainFrameVO.builder().mine(userVO).friend(friendGroupVOList).group(groupVOList).build();
        return mainFrameVO;
    }

    @Override
    public MemberVO initMembers(Integer groupId) {
        List<Integer> userIds = restService.findUserIdByGroupId(groupId).getData();
        List<UserVO> userVOList = new ArrayList<>(userIds.size());
        userIds.forEach(userId -> {
            UserResponse userResponse = restService.queryUser(userId).getData();
            userVOList.add(buildUserVO(userResponse));
        });
        return MemberVO.builder().list(userVOList).build();
    }

    public UserVO buildUserVO(UserResponse userResponse) {
        UserVO userVO = UserVO.builder()
                .avatar(userResponse.getHeadPortrait())
                .id(userResponse.getId().toString())
                .status(restService.queryUserStateById(userResponse.getUserStateId()).getData().getName())
                .username(userResponse.getNickName())
                .sign(userResponse.getSignature())
                .build();
        return userVO;
    }
}

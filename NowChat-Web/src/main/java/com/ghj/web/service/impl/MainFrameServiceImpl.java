package com.ghj.web.service.impl;

import com.ghj.common.base.Constant;
import com.ghj.common.dto.response.*;
import com.ghj.web.service.MainFrameService;
import com.ghj.web.service.RestService;
import com.ghj.web.vo.*;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public List<MessageVO> initOffLineMessages(Integer toUserId, Boolean status) {
        List<GroupMessageToUserResponse> groupMessageToUserResponseList = restService.listGroupMessageByToUserIdAndStatus(toUserId, status).getData();
        List<MessageResponse> messageResponseList = restService.queryMessageWithToUserIdAndStatus(toUserId, status).getData();
        List<MessageVO> messageVOList = new ArrayList<>(messageResponseList.size() + groupMessageToUserResponseList.size());
        groupMessageToUserResponseList.forEach(groupMessageToUserResponse -> {
            messageVOList.add(buildGroupMessageVO(groupMessageToUserResponse));
        });
        messageResponseList.forEach(messageResponse -> {
            messageVOList.add(buildMessageVO(messageResponse));
        });
        return messageVOList;
    }

    @Override
    public HistoryMessageVO initHistoryMessage(Integer fromUserId, Integer toUserId,  String type, Integer pageIndex, Integer pageSize) {
        if (Constant.MESSAGE_TO_PERSONAL.equals(type)) {
            HistoryMessage<MessageResponse> historyMessage = restService.queryHistoryMessageListForPage(fromUserId, toUserId, pageIndex, pageSize).getData();
            List<MessageVO> messageVOList = new ArrayList<>(historyMessage.getData().size());
            historyMessage.getData().forEach(messageResponse -> {
                messageVOList.add(buildMessageVO(messageResponse));
            });
            return HistoryMessageVO.builder().data(messageVOList)
                    .pageNum(historyMessage.getPageNum()).pageSize(historyMessage.getPageSize()).total(historyMessage.getTotal()).build();
        } else {
            HistoryMessage<GroupMessageToUserResponse> historyMessage = restService.queryHistoryGroupMessageListForPage(toUserId, pageIndex, pageSize).getData();
            List<MessageVO> messageVOList = new ArrayList<>(historyMessage.getData().size());
            historyMessage.getData().forEach(groupMessageToUserResponse -> {
                messageVOList.add(buildGroupMessageVO(groupMessageToUserResponse));
            });
            return HistoryMessageVO.builder().data(messageVOList)
                    .pageNum(historyMessage.getPageNum()).pageSize(historyMessage.getPageSize()).total(historyMessage.getTotal()).build();
        }
    }



    private UserVO buildUserVO(UserResponse userResponse) {
        return UserVO.builder()
                .avatar(userResponse.getHeadPortrait())
                .id(userResponse.getId().toString())
                .status(restService.queryUserStateById(userResponse.getUserStateId()).getData().getName())
                .username(userResponse.getNickName())
                .sign(userResponse.getSignature())
                .build();
    }

    private MessageVO buildMessageVO(MessageResponse messageResponse) {
        Integer id = messageResponse.getFromUserId();
        UserResponse userResponse = restService.queryUser(id).getData();
        return MessageVO.builder()
                .avatar(userResponse.getHeadPortrait())
                .cid(messageResponse.getId())
                .fromid(messageResponse.getFromUserId().toString())
                .content(messageResponse.getPostMessage())
                .id(messageResponse.getFromUserId().toString())
                .timestamp(messageResponse.getSendTime())
                .username(userResponse.getNickName())
                .type(Constant.MESSAGE_TO_PERSONAL)
                .build();
    }

    private MessageVO buildGroupMessageVO(GroupMessageToUserResponse groupMessageToUserResponse) {
        Integer id = groupMessageToUserResponse.getFromUserId();
        UserResponse userResponse = restService.queryUser(id).getData();
        return MessageVO.builder()
                .avatar(userResponse.getHeadPortrait())
                .cid(groupMessageToUserResponse.getGroupMessageId())
                .content(groupMessageToUserResponse.getContent())
                .id(groupMessageToUserResponse.getToGroupId().toString())
                .fromid(groupMessageToUserResponse.getFromUserId().toString())
                .timestamp(groupMessageToUserResponse.getSendTime())
                .username(userResponse.getNickName())
                .type(Constant.MESSAGE_TO_GROUP)
                .build();
    }


}

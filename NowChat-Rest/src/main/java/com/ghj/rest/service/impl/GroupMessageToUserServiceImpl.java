package com.ghj.rest.service.impl;

import com.ghj.common.dto.response.GroupMessageToUserResponse;
import com.ghj.common.dto.response.GroupToUserResponse;
import com.ghj.common.dto.response.HistoryMessage;
import com.ghj.common.dto.response.MessageResponse;
import com.ghj.rest.dao.GroupMessageMapper;
import com.ghj.rest.dao.GroupMessageToUserMapper;
import com.ghj.rest.model.GroupMessage;
import com.ghj.rest.model.GroupMessageToUser;
import com.ghj.rest.model.GroupToUser;
import com.ghj.rest.model.Message;
import com.ghj.rest.service.GroupMessageToUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMessageToUserServiceImpl implements GroupMessageToUserService {

    @Resource
    GroupMessageToUserMapper groupMessageToUserMapper;

    @Resource
    GroupMessageMapper groupMessageMapper;

    @Override
    public void insert(GroupMessageToUser groupMessageToUser) {
        groupMessageToUserMapper.insert(groupMessageToUser);
    }
    //TODO  群聊消息表应该缺少一个对应的组ID
    @Override
    public List<GroupMessageToUserResponse> listMessageByToUserIdAndStatus(Integer toUserId, Boolean status) {
        List<GroupMessageToUser> groupMessageToUsers = groupMessageToUserMapper.selectMessageByToUserIdAndStatus(toUserId, status);
        return buildGroupMessageToUserResponseList(groupMessageToUsers);
    }



    List<GroupMessageToUserResponse> buildGroupMessageToUserResponseList(List<GroupMessageToUser> groupMessageToUsers) {
        List<GroupMessageToUserResponse> groupMessageToUserResponseList = new ArrayList<>(groupMessageToUsers.size());
        groupMessageToUsers.forEach(groupMessageToUser -> {
            GroupMessageToUserResponse groupMessageToUserResponse = new GroupMessageToUserResponse();
            BeanUtils.copyProperties(groupMessageToUser, groupMessageToUserResponse);
            GroupMessage groupMessage = groupMessageMapper.selectByPrimaryKey(groupMessageToUser.getGroupMessageId());
            groupMessageToUserResponse.setFromUserId(groupMessage.getFromUserId());
            groupMessageToUserResponseList.add(groupMessageToUserResponse);
        });
        return groupMessageToUserResponseList;
    }



}

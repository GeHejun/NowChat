package com.ghj.rest.service.impl;

import com.ghj.common.dto.response.GroupMessageToUserResponse;
import com.ghj.rest.dao.GroupMessageMapper;
import com.ghj.rest.dao.GroupMessageToUserMapper;
import com.ghj.rest.model.GroupMessage;
import com.ghj.rest.model.GroupMessageToUser;
import com.ghj.rest.service.GroupMessageToUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author GeHejun
 */
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


    @Override
    public List<GroupMessageToUserResponse> listMessageByToUserIdAndStatus(Integer toUserId, Boolean status) {
        List<GroupMessageToUser> groupMessageToUsers = groupMessageToUserMapper.selectMessageByToUserIdAndStatus(toUserId, status);
        //TODO 暂时在这里把消息置为已读
        groupMessageToUsers.forEach(groupMessageToUser -> {
            groupMessageToUser.setSate(Boolean.TRUE);
            groupMessageToUserMapper.updateByPrimaryKey(groupMessageToUser);
        });
        return buildGroupMessageToUserResponseList(groupMessageToUsers);
    }



    List<GroupMessageToUserResponse> buildGroupMessageToUserResponseList(List<GroupMessageToUser> groupMessageToUsers) {
        List<GroupMessageToUserResponse> groupMessageToUserResponseList = new ArrayList<>(groupMessageToUsers.size());
        groupMessageToUsers.forEach(groupMessageToUser -> {
            GroupMessageToUserResponse groupMessageToUserResponse = new GroupMessageToUserResponse();
            BeanUtils.copyProperties(groupMessageToUser, groupMessageToUserResponse);
            GroupMessage groupMessage = groupMessageMapper.selectByPrimaryKey(groupMessageToUser.getGroupMessageId());
            if (!Objects.isNull(groupMessage)) {
                groupMessageToUserResponse.setFromUserId(groupMessage.getFromUserId());
            }
            groupMessageToUserResponseList.add(groupMessageToUserResponse);
        });
        return groupMessageToUserResponseList;
    }



}

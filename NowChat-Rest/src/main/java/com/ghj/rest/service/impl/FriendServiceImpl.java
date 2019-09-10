package com.ghj.rest.service.impl;

import com.ghj.common.base.Constant;
import com.ghj.common.dto.response.FriendResponse;
import com.ghj.rest.dao.FriendGroupMapper;
import com.ghj.rest.dao.FriendMapper;
import com.ghj.rest.dao.SystemMessageMapper;
import com.ghj.rest.dao.UserMapper;
import com.ghj.rest.model.Friend;
import com.ghj.rest.model.FriendGroup;
import com.ghj.rest.model.SystemMessage;
import com.ghj.rest.model.User;
import com.ghj.rest.service.FriendService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gehj
 * @date 2019/6/2511:36
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    FriendMapper friendMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    SystemMessageMapper systemMessageMapper;

    @Resource
    FriendGroupMapper friendGroupMapper;

    @Override
    public List<FriendResponse> listFriendsByUserId(Integer userId) {
        List<Friend> friendList = friendMapper.listFriendListByUserId(userId);
        List<FriendResponse> friendResponseList = new ArrayList<>(friendList.size());
        friendList.stream().forEach(friend -> {
            FriendResponse friendResponse = new FriendResponse();
            BeanUtils.copyProperties(friend, friendResponse);
            friendResponseList.add(friendResponse);
        });
        return friendResponseList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean agreeFriend(Long validationMessageId, Integer fromUserId, Integer fromFriendGroupId, Integer toUserId, Integer toFriendGroupId, String newFriendGroupName) {
        try {
            if (newFriendGroupName != null) {
                FriendGroup friendGroup = new FriendGroup();
                friendGroup.setName(newFriendGroupName);
                friendGroup.setUserId(toUserId);
                friendGroupMapper.insertAndGetId(friendGroup);
                toFriendGroupId = friendGroup.getId();
            }
            SystemMessage systemMessage = systemMessageMapper.selectByPrimaryKey(validationMessageId);
            systemMessage.setHandleResult(Constant.AGREE_VALIDATION_MESSAGE);
            systemMessageMapper.updateByPrimaryKey(systemMessage);
            User toUser = userMapper.selectByPrimaryKey(toUserId);
            Friend toFriend = new Friend();
            toFriend.setUserId(fromUserId);
            toFriend.setFriendId(toUserId);
            toFriend.setName(toUser.getName());
            toFriend.setFriendGroupId(toFriendGroupId);
            friendMapper.insert(toFriend);
            User fromUser = userMapper.selectByPrimaryKey(fromUserId);
            Friend fromFriend = new Friend();
            fromFriend.setUserId(toUserId);
            fromFriend.setFriendId(fromUserId);
            fromFriend.setFriendGroupId(fromFriendGroupId);
            fromFriend.setName(fromUser.getName());
            friendMapper.insert(fromFriend);
        } catch (RuntimeException e) {
            return  false;
        }
        return true;
    }

    @Override
    public Boolean refuseFriend(Long validationMessageId) {
        try {
            SystemMessage systemMessage = systemMessageMapper.selectByPrimaryKey(validationMessageId);
            systemMessage.setHandleResult(Constant.REFUSE_VALIDATION_MESSAGE);
            systemMessageMapper.updateByPrimaryKey(systemMessage);
        } catch (RuntimeException e) {
            return  false;
        }
        return true;

    }
}

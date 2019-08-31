package com.ghj.rest.service.impl;

import com.ghj.common.dto.response.GroupToUserResponse;
import com.ghj.rest.dao.GroupMessageMapper;
import com.ghj.rest.dao.GroupMessageToUserMapper;
import com.ghj.rest.dao.GroupToUserMapper;
import com.ghj.rest.model.GroupToUser;
import com.ghj.rest.service.GroupMessageService;
import com.ghj.rest.service.GroupToUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupToUserServiceImpl implements GroupToUserService {

    @Resource
    GroupToUserMapper groupToUserMapper;



    @Override
    public List<GroupToUserResponse> findGroupByUserId(Integer userId) {
        List<GroupToUser> groupToUserList = groupToUserMapper.selectGroupToUserByUserId(userId);
        List<GroupToUserResponse> groupToUserResponseList = new ArrayList<>(groupToUserList.size());
        groupToUserList.stream().forEach(groupToUser -> {
            GroupToUserResponse groupToUserResponse = new GroupToUserResponse();
            BeanUtils.copyProperties(groupToUser, groupToUserResponse);
            groupToUserResponseList.add(groupToUserResponse);
        });
        return groupToUserResponseList;
    }

    @Override
    public List<Integer> findUserIdByGroupId(Integer groupId) {
        List<GroupToUser> groupToUserList = groupToUserMapper.selectGroupToUserByGroupId(groupId);
        List<Integer> userIds = new ArrayList<>(groupToUserList.size());
        groupToUserList.stream().forEach(groupToUser -> userIds.add(groupToUser.getToUserId()));
        return userIds;
    }

}

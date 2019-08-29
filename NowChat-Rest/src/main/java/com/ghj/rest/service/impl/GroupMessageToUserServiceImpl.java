package com.ghj.rest.service.impl;

import com.ghj.rest.dao.GroupMessageToUserMapper;
import com.ghj.rest.model.GroupMessageToUser;
import com.ghj.rest.service.GroupMessageToUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GroupMessageToUserServiceImpl implements GroupMessageToUserService {
    @Resource
    GroupMessageToUserMapper groupMessageToUserMapper;
    @Override
    public void insert(GroupMessageToUser groupMessageToUser) {
        groupMessageToUserMapper.insert(groupMessageToUser);
    }
}

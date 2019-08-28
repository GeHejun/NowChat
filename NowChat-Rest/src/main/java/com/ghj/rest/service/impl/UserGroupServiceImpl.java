package com.ghj.rest.service.impl;

import com.ghj.common.dto.response.UserGroupResponse;
import com.ghj.rest.dao.UserGroupMapper;
import com.ghj.rest.model.UserGroup;
import com.ghj.rest.service.UserGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Resource
    UserGroupMapper userGroupMapper;
    @Override
    public UserGroupResponse findGroupById(Integer id) {
        UserGroup userGroup = userGroupMapper.selectByPrimaryKey(id);
        UserGroupResponse userGroupResponse = new UserGroupResponse();
        BeanUtils.copyProperties(userGroup, userGroupResponse);
        return userGroupResponse;
    }
}
package com.ghj.rest.service.impl;

import com.ghj.common.dto.response.UserGroupResponse;
import com.ghj.rest.dao.UserGroupMapper;
import com.ghj.rest.model.UserGroup;
import com.ghj.rest.service.UserGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GeHejun
 */
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

    @Override
    public List<UserGroupResponse> findGroupByName(String name) {
        List<UserGroup> userGroupList = userGroupMapper.selectGroupByName(name);
        List<UserGroupResponse> userGroupResponseList = new ArrayList<>(userGroupList.size());
        userGroupList.stream().forEach(userGroup -> {
            UserGroupResponse userGroupResponse = new UserGroupResponse();
            BeanUtils.copyProperties(userGroup, userGroupResponse);
            userGroupResponseList.add(userGroupResponse);
        });
        return userGroupResponseList;
    }


}

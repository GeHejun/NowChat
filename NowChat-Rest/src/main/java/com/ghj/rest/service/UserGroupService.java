package com.ghj.rest.service;

import com.ghj.common.dto.response.UserGroupResponse;
import com.ghj.rest.model.UserGroup;

public interface UserGroupService {
    UserGroupResponse findGroupById(Integer id);
}

package com.ghj.rest.service;

import com.ghj.common.dto.response.UserGroupResponse;

/**
 * @author GeHejun
 */
public interface UserGroupService {
    /**
     * 通过id查找群
     * @param id
     * @return
     */
    UserGroupResponse findGroupById(Integer id);
}

package com.ghj.rest.service;

import com.ghj.common.base.Code;
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

    /**
     * 通过群名称查询群
     * @param name
     * @return
     */
    UserGroupResponse findGroupByName(String name);
}

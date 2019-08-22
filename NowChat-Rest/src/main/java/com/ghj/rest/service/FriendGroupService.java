package com.ghj.rest.service;

import com.ghj.common.dto.response.FriendGroupResponse;

/**
 * @author gehj
 * @version 1.0
 * @description 获取分组
 * @date 2019/8/22 10:54
 */
public interface FriendGroupService {

    FriendGroupResponse queryGroupById(Integer id);
}

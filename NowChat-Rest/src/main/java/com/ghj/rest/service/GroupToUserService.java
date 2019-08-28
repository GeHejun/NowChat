package com.ghj.rest.service;

import com.ghj.common.base.Code;
import com.ghj.common.dto.response.GroupToUserResponse;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface GroupToUserService {
    List<GroupToUserResponse> findGroupByUserId(Integer userId);

    List<Integer> findUserIdByGroupId(Integer groupId);
}

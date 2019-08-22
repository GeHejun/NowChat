package com.ghj.web.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.FriendGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 11:03
 */
@FeignClient(name = "rest")
public interface FriendGroupService {

    @RequestMapping("/queryGroupById")
    Result<FriendGroupResponse> queryGroupById(@NotNull Integer id);
}

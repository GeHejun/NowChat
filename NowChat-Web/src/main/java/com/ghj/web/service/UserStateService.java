package com.ghj.web.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.UserStateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 11:40
 */
@FeignClient(name = "rest")
public interface UserStateService {

    /**
     * 查询状态信息
     * @param id
     * @return
     */
    @RequestMapping("/")
    Result<UserStateResponse> queryUserStateById(@NotNull Integer id);
}

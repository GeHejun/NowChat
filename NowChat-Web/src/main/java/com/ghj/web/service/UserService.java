package com.ghj.web.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 10:17
 */
@FeignClient(name = "rest")
public interface UserService {

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    @ResponseBody
    Result<UserResponse> queryUser(@NotNull Integer id);
}

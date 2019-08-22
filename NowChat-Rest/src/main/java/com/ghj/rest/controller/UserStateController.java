package com.ghj.rest.controller;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.UserStateResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 11:35
 */
@RestController
@RequestMapping("/state")
public class UserStateController {

    @RequestMapping("/")
    public Result<UserStateResponse> queryUserStateById(@NotNull Integer id) {
        return null;
    }
}

package com.ghj.rest.controller;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.rest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<UserResponse> login(@NotNull String loginName, @NotNull String passWord) {
        UserResponse userResponse = userService.validateUser(loginName, passWord);
        return Result.defaultSuccess(userResponse);
    }

    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    @ResponseBody
    public Result<UserResponse> queryUser(@NotNull Integer id) {
        UserResponse userResponse = userService.getUserById(id);
        return Result.defaultSuccess(userResponse);
    }
}

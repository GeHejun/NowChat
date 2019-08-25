package com.ghj.rest.controller;

import com.ghj.common.base.Code;
import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.common.exception.UserException;
import com.ghj.rest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Result<UserResponse> login(@NotNull @RequestParam("loginName") String loginName, @NotNull @RequestParam("password") String password) {
        try {
            UserResponse userResponse = userService.validateUser(loginName, password);
            return Result.defaultSuccess(Code.LOGIN_SUCCESS, userResponse);
        } catch (UserException e) {
            return Result.failure(Constant.LOGIN_FAILURE_CODE, Constant.LOGIN_FAILURE);
        }
    }

    @RequestMapping(value = "/queryUser")
    @ResponseBody
    public Result<UserResponse> queryUser(@NotNull @RequestParam("id") Integer id) {
        UserResponse userResponse = userService.getUserById(id);
        return Result.defaultSuccess(userResponse);
    }
}

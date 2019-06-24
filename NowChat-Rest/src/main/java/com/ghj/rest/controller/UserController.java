package com.ghj.rest.controller;

import com.ghj.common.base.Result;
import com.ghj.rest.dto.UserRequest;
import com.ghj.rest.dto.UserResponse;
import com.ghj.rest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    public Result<UserResponse> login(@RequestParam @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.validateUser(userRequest);
        return Result.defaultSuccess(userResponse);
    }
}

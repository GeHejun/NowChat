package com.ghj.web.controller;

import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.common.exception.UserException;
import com.ghj.common.util.DesEncryptDecrypt;
import com.ghj.web.service.SecurityService;
import com.ghj.web.vo.ResultVO;
import com.ghj.web.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Resource
    SecurityService securityService;

    @RequestMapping(value = "/validateUser", method = RequestMethod.POST)
    public ResultVO<UserVO> validateUser(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        try {
            UserVO userVO = securityService.validateUser(loginName, password);
            return ResultVO.defaultSuccess(userVO);
        } catch (UserException e) {
            return ResultVO.failure(Constant.LOGIN_FAILURE_CODE, Constant.LOGIN_FAILURE);
        }
    }

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public ResultVO<Boolean> checkUser(@RequestParam("loginName") String loginName) {
        try {
            Boolean isOk = securityService.checkUser(loginName);
            return ResultVO.defaultSuccess(isOk);
        } catch (UserException e) {
            return ResultVO.failure(Constant.REGISTER_FAILURE_CODE, Constant.REGISTER_FAILURE);
        }
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultVO<UserResponse> register(@RequestBody UserRequest userRequest) {
        try {
            userRequest.setPassWord(DesEncryptDecrypt.getInstance().encrypt(userRequest.getPassWord()));
            UserResponse userResponse = securityService.register(userRequest);
            return ResultVO.defaultSuccess(userResponse);
        } catch (UserException e) {
            return ResultVO.failure(Constant.REGISTER_FAILURE_CODE, Constant.REGISTER_FAILURE);
        }
    }
}

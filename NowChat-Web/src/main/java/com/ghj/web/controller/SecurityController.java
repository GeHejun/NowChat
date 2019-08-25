package com.ghj.web.controller;

import com.ghj.common.base.Constant;
import com.ghj.common.exception.UserException;
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
}

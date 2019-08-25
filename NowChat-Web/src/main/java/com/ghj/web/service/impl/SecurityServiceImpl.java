package com.ghj.web.service.impl;

import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.common.exception.UserException;
import com.ghj.web.service.RestService;
import com.ghj.web.service.SecurityService;
import com.ghj.web.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    RestService restService;


    @Override
    public UserVO validateUser(String loginName, String password) {
        Result<UserResponse> result = restService.login(loginName, password);
        if (!(result.isSuccess() && Constant.LOGIN_SUCCESS_CODE == result.getCode())) {
            throw new UserException();
        }
        UserResponse userResponse = result.getData();
        UserVO userVO = UserVO.builder()
                .id(userResponse.getId().toString())
                .username(userResponse.getName())
                .token(userResponse.getToken())
                .build();
        return userVO;
    }
}

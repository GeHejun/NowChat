package com.ghj.web.service;


import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.web.vo.UserVO;

public interface SecurityService {
    UserVO validateUser(String loginName, String password);

    Boolean checkUser(String loginName);

    UserResponse register(UserRequest userRequest);
}

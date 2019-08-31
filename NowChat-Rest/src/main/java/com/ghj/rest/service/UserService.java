package com.ghj.rest.service;

import com.ghj.common.base.Code;
import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public interface UserService {

    UserResponse validateUser(String loginName, String password);

    UserResponse getUserById(Integer id);

    Boolean checkUser(String loginName);

    UserResponse register(UserRequest userRequest);
}

package com.ghj.rest.service;

import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public interface UserService {

    UserResponse validateUser(UserRequest userRequest);

    UserResponse getUserById(Integer id);
}

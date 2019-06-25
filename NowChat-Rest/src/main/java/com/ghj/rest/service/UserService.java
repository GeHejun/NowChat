package com.ghj.rest.service;

import com.ghj.rest.dto.UserRequest;
import com.ghj.rest.dto.UserResponse;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public interface UserService {

    UserResponse validateUser(UserRequest userRequest);

    UserResponse getUserById(Integer id);
}

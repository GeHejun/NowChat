package com.ghj.rest.service;

import com.ghj.rest.dto.UserRequest;
import com.ghj.rest.dto.UserResponse;

public interface UserService {

    UserResponse validateUser(UserRequest userRequest);
}

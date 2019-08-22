package com.ghj.rest.service;

import com.ghj.common.dto.response.UserResponse;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public interface UserService {

    UserResponse validateUser(String loginName, String password);

    UserResponse getUserById(Integer id);
}

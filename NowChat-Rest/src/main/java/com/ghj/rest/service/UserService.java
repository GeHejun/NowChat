package com.ghj.rest.service;

import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public interface UserService {
    /**
     * 验证用户
     * @param loginName
     * @param password
     * @return
     */
    UserResponse validateUser(String loginName, String password);

    /**
     * 通过id查找用户信息
     * @param id
     * @return
     */
    UserResponse getUserById(Integer id);

    /**
     * 检查用户名是否可用
     * @param loginName
     * @return
     */
    Boolean checkUser(String loginName);

    /**
     * 用户注册接口
     * @param userRequest
     * @return
     */
    UserResponse register(UserRequest userRequest);
}

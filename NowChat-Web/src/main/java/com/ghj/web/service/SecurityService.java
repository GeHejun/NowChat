package com.ghj.web.service;


import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.web.vo.UserVO;

/**
 * @author GeHejun
 */
public interface SecurityService {
    /**
     * 校验用户信息
     * @param loginName
     * @param password
     * @return
     */
    UserVO validateUser(String loginName, String password);

    /**
     * 检查用户名是否可用
     * @param loginName
     * @return
     */
    Boolean checkUser(String loginName);

    /**
     * 注册新用户
     * @param userRequest
     * @return
     */
    UserResponse register(UserRequest userRequest);
}

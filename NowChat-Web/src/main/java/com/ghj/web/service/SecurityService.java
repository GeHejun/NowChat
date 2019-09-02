package com.ghj.web.service;


import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.web.vo.GroupVO;
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

    /**
     * 通过用户名查找用户
     * @param loginName
     * @return
     */
    UserVO findUser(String loginName);

    /**
     * 通过群名称查询群
     * @param name
     * @return
     */
    GroupVO findGroup(String name);
}

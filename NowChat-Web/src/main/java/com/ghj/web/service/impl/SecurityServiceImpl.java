package com.ghj.web.service.impl;

import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserGroupResponse;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.common.exception.UserException;
import com.ghj.web.service.RestService;
import com.ghj.web.service.SecurityService;
import com.ghj.web.vo.GroupVO;
import com.ghj.web.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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

    @Override
    public Boolean checkUser(String loginName) {
        return restService.checkUser(loginName).getData();
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        return restService.register(userRequest).getData();
    }

    @Override
    public UserVO findUser(String loginName) {
        UserResponse userResponse = restService.queryUserByLoginName(loginName).getData();
        if (Objects.isNull(userResponse)) {
            throw new RuntimeException();
        }
        UserVO userVO = UserVO.builder()
                .id(userResponse.getId().toString())
                .username(userResponse.getName())
                .token(userResponse.getToken())
                .build();
        return userVO;
    }

    @Override
    public GroupVO findGroup(String name) {
        UserGroupResponse userGroupResponse = restService.findGroupByName(name).getData();
        GroupVO groupVO = GroupVO.builder().avatar(userGroupResponse.getIcon()).groupname(userGroupResponse.getName())
                .id(userGroupResponse.getId().toString()).build();
        return groupVO;
    }
}

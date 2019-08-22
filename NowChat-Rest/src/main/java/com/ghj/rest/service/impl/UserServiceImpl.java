package com.ghj.rest.service.impl;

import com.ghj.common.base.Constant;
import com.ghj.common.util.DesEncryptDecrypt;
import com.ghj.common.exception.UserException;
import com.ghj.rest.dao.UserMapper;
import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.rest.model.User;
import com.ghj.rest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public UserResponse validateUser(UserRequest userRequest) {
        User user = userMapper.selectUserByLoginName(userRequest.getLoginName());
        if (Objects.isNull(user)) {
            throw new UserException();
        }
        String encrypt = DesEncryptDecrypt.getInstance().encrypt(userRequest.getPassWord());
        if (encrypt.equals(user.getPassWord())) {
            throw new UserException();
        }
        String token = user.getId()+"_"+ UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set(user.getId() + Constant.USER_TOKEN_KEY, token);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        userResponse.setToken(token);
        return userResponse;
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }
}

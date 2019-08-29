package com.ghj.rest.service.impl;

import com.ghj.common.base.Constant;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.common.exception.UserException;
import com.ghj.common.util.DesEncryptDecrypt;
import com.ghj.rest.dao.UserMapper;
import com.ghj.rest.model.User;
import com.ghj.rest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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

    RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }


    @Override
    public UserResponse validateUser(String loginName, String password) {
        User user = userMapper.selectUserByLoginName(loginName);
        if (Objects.isNull(user)) {
            throw new UserException();
        }
        String encrypt = DesEncryptDecrypt.getInstance().encrypt(password);
        if (!encrypt.equals(user.getPassWord())) {
            throw new UserException();
        }
        String token = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set(Constant.SYSTEM_PREFIX + Constant.USER_TOKEN_KEY + user.getId() , token);
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

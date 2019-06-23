package com.ghj.rest.dao;

import com.ghj.rest.dto.UserRequest;
import com.ghj.rest.model.User;
import org.springframework.stereotype.Repository;

/**
 * UserMapper继承基类
 */
@Repository
public interface UserMapper extends MyBatisBaseDao<User, Integer> {
    User selectUserByLoginName(String loginName);
}
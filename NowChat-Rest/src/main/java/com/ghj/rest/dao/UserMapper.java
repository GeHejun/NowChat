package com.ghj.rest.dao;

import com.ghj.rest.model.User;

/**
 * UserMapper继承基类
 */
public interface UserMapper extends MyBatisBaseDao<User, Integer> {
    User selectUserByLoginName(String loginName);
}
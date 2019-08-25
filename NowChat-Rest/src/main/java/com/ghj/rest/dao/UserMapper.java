package com.ghj.rest.dao;

import com.ghj.rest.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper继承基类
 */
public interface UserMapper extends MyBatisBaseDao<User, Integer> {
    User selectUserByLoginName(@Param("loginName") String loginName);
}
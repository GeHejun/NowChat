package com.ghj.rest.dao;

import com.ghj.rest.model.UserState;
import org.springframework.stereotype.Repository;

/**
 * UserStateMapper继承基类
 */
@Repository
public interface UserStateMapper extends MyBatisBaseDao<UserState, Integer> {
}
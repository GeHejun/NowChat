package com.ghj.rest.dao;

import com.ghj.rest.model.UserGroup;
import org.springframework.stereotype.Repository;

/**
 * UserGroupMapper继承基类
 */
@Repository
public interface UserGroupMapper extends MyBatisBaseDao<UserGroup, Integer> {
}
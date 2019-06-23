package com.ghj.rest.dao;

import com.ghj.rest.model.GroupToUser;
import org.springframework.stereotype.Repository;

/**
 * GroupToUserMapper继承基类
 */
@Repository
public interface GroupToUserMapper extends MyBatisBaseDao<GroupToUser, Integer> {
}
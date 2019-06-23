package com.ghj.rest.dao;

import com.ghj.rest.model.FriendshipPolicy;
import org.springframework.stereotype.Repository;

/**
 * FriendshipPolicyMapper继承基类
 */
@Repository
public interface FriendshipPolicyMapper extends MyBatisBaseDao<FriendshipPolicy, Integer> {
}
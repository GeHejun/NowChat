package com.ghj.rest.dao;

import com.ghj.rest.model.FriendGroup;
import org.springframework.stereotype.Repository;

/**
 * FriendGroupMapper继承基类
 */
@Repository
public interface FriendGroupMapper extends MyBatisBaseDao<FriendGroup, Integer> {
}
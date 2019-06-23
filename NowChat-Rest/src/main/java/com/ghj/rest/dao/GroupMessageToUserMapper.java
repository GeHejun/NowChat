package com.ghj.rest.dao;

import com.ghj.rest.model.GroupMessageToUser;
import org.springframework.stereotype.Repository;

/**
 * GroupMessageToUserMapper继承基类
 */
@Repository
public interface GroupMessageToUserMapper extends MyBatisBaseDao<GroupMessageToUser, Integer> {
}
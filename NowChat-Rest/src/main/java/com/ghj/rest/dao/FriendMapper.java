package com.ghj.rest.dao;

import com.ghj.rest.model.Friend;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FriendMapper继承基类
 */
public interface FriendMapper extends MyBatisBaseDao<Friend, Integer> {
    List<Friend> listFriendListByUserId(Integer userId);
}
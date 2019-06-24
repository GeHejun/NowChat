package com.ghj.rest.dao;

import com.ghj.rest.model.Message;
import org.springframework.stereotype.Repository;

/**
 * MessageMapper继承基类
 */
public interface MessageMapper extends MyBatisBaseDao<Message, Integer> {
}
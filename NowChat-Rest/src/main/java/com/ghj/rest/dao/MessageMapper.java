package com.ghj.rest.dao;

import com.ghj.rest.model.Message;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MessageMapper继承基类
 */
public interface MessageMapper extends MyBatisBaseDao<Message, Integer> {
    List<Message> listMessageBytoUserId(Integer userId);

    List<Message> listMessageByToUserIdWithStatus(Integer toUserId, Boolean status);
}
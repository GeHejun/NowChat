package com.ghj.rest.dao;

import com.ghj.rest.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MessageMapper继承基类
 */
public interface MessageMapper extends MyBatisBaseDao<Message, Integer> {

    List<Message> listMessageByToUserId(@Param("toUserId") Integer toUserId);

    List<Message> listMessageByToUserIdWithStatus(@Param("toUserId") Integer toUserId,@Param("status") Boolean status);
}
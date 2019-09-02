package com.ghj.rest.service;


import com.ghj.common.dto.response.HistoryMessage;
import com.ghj.common.dto.response.MessageResponse;
import com.ghj.rest.model.Message;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author gehj
 * @date 2019/6/2517:47
 */
public interface MessageService {
    /**
     * 查找历史消息
     * @param fromUserId
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    HistoryMessage<MessageResponse> queryHistoryMessageListForPage(Integer fromUserId, Integer userId, Integer pageIndex, Integer pageSize);

    /**
     * 通过消息id获取消息
     * @param id
     * @return
     */
    MessageResponse queryMessageById(Integer id);

    /**
     * 新增消息
     * @param message
     */
    void insertMessage(Message message);

    /**
     * 通过状态和接收人查找消息
     * @param toUserId
     * @param status
     * @return
     */
    List<MessageResponse> queryMessageByToUserIdWithStatus(Integer toUserId, Boolean status);
}

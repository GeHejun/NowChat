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
    HistoryMessage<MessageResponse> queryHistoryMessageListForPage(Integer userId, Integer pageIndex, Integer pageSize);

    MessageResponse queryMessageById(Integer id);

    void insertMessage(Message message);

    List<MessageResponse> queryMessageByToUserIdWithStatus(Integer toUserId, Boolean status);
}

package com.ghj.rest.service.impl;


import com.ghj.common.dto.response.HistoryMessage;
import com.ghj.rest.dao.MessageMapper;
import com.ghj.common.dto.response.MessageResponse;
import com.ghj.rest.model.Message;
import com.ghj.rest.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gehj
 * @date 2019/6/25 17:47
 */
@Service
public class MessageServiceImpl implements MessageService {


    @Resource
    MessageMapper messageMapper;


    @Override
    public HistoryMessage<MessageResponse> queryHistoryMessageListForPage(Integer fromUserId, Integer toUserId, Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Message> messageList = messageMapper.selectMessageByToUserId(fromUserId,toUserId);
        PageInfo<MessageResponse> pageInfo = new PageInfo<>(buildMessageResponseList(messageList));
        HistoryMessage<MessageResponse> historyMessage = new HistoryMessage<>();
        historyMessage.setPageNum(pageInfo.getPageNum());
        historyMessage.setPageSize(pageInfo.getPageSize());
        historyMessage.setData(pageInfo.getList());
        historyMessage.setTotal(pageInfo.getTotal());
        return historyMessage;
    }

    @Override
    public MessageResponse queryMessageById(Integer id) {
        Message message = messageMapper.selectByPrimaryKey(id);
        MessageResponse messageResponse = new MessageResponse();
        BeanUtils.copyProperties(message, messageResponse);
        return messageResponse;
    }

    @Override
    public void insertMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public List<MessageResponse> queryMessageByToUserIdWithStatus(Integer toUserId, Boolean status) {
        List<Message> messageList = messageMapper.selectMessageByToUserIdWithStatus(toUserId, status);
        //TODO 暂时在这里把消息置为已读
        messageList.forEach(message -> {
            message.setStatus(Boolean.TRUE);
            messageMapper.updateByPrimaryKey(message);
        });
        return buildMessageResponseList(messageList);
    }


    List<MessageResponse> buildMessageResponseList(List<Message> messageList) {
        List<MessageResponse> messageResponseList = new ArrayList<>(messageList.size());
        messageList.forEach(message -> {
            MessageResponse messageResponse = new MessageResponse();
            BeanUtils.copyProperties(message, messageResponse);
            messageResponseList.add(messageResponse);
        });
        return messageResponseList;
    }


}

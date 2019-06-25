package com.ghj.rest.service.impl;

import com.ghj.common.protocol.MessageProto;
import com.ghj.rest.dao.MessageMapper;
import com.ghj.rest.dto.MessageResponse;
import com.ghj.rest.model.Message;
import com.ghj.rest.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gehj
 * @date 2019/6/25 17:47
 */
public class MessageServiceImpl implements MessageService {


    @Resource
    MessageMapper messageMapper;


    @Override
    public PageInfo<MessageResponse> listMessagePage(Integer userId, Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Message> messageList = messageMapper.listMessageBytoUserId(userId);
        List<MessageResponse> messageResponseList = new ArrayList<>(messageList.size());
        messageList.forEach(message -> {
            MessageResponse messageResponse = new MessageResponse();
            BeanUtils.copyProperties(message, messageResponse);
            messageResponseList.add(messageResponse);
        });
        PageInfo<MessageResponse> pageInfo = new PageInfo<>(messageResponseList);
        return pageInfo;
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
}

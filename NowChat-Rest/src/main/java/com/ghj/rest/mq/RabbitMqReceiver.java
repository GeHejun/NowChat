package com.ghj.rest.mq;

import com.ghj.common.base.Constant;
import com.ghj.common.dto.AbstractMessage;
import com.ghj.common.dto.MessageToGroup;
import com.ghj.common.dto.MessageToUser;
import com.ghj.common.util.JSONUtil;
import com.ghj.rest.model.GroupMessage;
import com.ghj.rest.model.Message;
import com.ghj.rest.service.MessageService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

//@Component
//@RabbitListener(queues = Constant.QUEUE_A)
public class RabbitMqReceiver {

    @Resource
    MessageService messageService;

    @RabbitHandler
    public void process(String content) {
        try {
            AbstractMessage message = (AbstractMessage) JSONUtil.jsonToBean(content, Message.class);
            if (message instanceof MessageToUser) {
                Message nativeMessage = new Message();
                nativeMessage.setId(message.getId());
                nativeMessage.setFromUserId(message.getFromUserId());
                nativeMessage.setId(message.getId());
                nativeMessage.setMessageTypeId(nativeMessage.getMessageTypeId());
                nativeMessage.setPostMessage(message.getPostMessage());
                nativeMessage.setSendTime(new Date());
                nativeMessage.setToUserId(((MessageToUser) message).getToUserId());
                messageService.insertMessage(nativeMessage);
            }
            if (message instanceof MessageToGroup) {
                GroupMessage groupMessage = new GroupMessage();
                groupMessage.setFromUserId( message.getFromUserId());
                groupMessage.setContent(message.getPostMessage());
                groupMessage.setSendTime(new Date());
            }

        } catch (Exception e) {

        }

    }
}

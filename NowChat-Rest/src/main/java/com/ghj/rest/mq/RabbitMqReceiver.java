package com.ghj.rest.mq;

import com.ghj.common.base.Constant;
import com.ghj.common.protocol.MessageProto;
import com.ghj.common.util.JSONUtil;
import com.ghj.rest.model.Message;
import com.ghj.rest.service.MessageService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Component
@RabbitListener(queues = Constant.QUEUE_A)
public class RabbitMqReceiver {

    @Resource
    MessageService messageService;

    @RabbitHandler
    public void process(String content) {
        try {
            MessageProto.message message = (MessageProto.message) JSONUtil.jsonToBean(content, MessageProto.message.class);
            Message nativeMessage = new Message();
            nativeMessage.setFromUserId(message.getFromUserId());
            nativeMessage.setId(message.getId());
            nativeMessage.setMessageTypeId(nativeMessage.getMessageTypeId());
            nativeMessage.setPostMessage(message.getContent());
            nativeMessage.setToUserId(message.getToUserId());
            nativeMessage.setSendTime(new Date());
            messageService.insertMessage(nativeMessage);
        } catch (Exception e) {

        }

    }
}

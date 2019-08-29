package com.ghj.rest.mq;

import com.ghj.common.base.Constant;
import com.ghj.common.dto.PersistentMessage;
import com.ghj.common.util.JSONUtil;
import com.ghj.rest.model.GroupMessage;
import com.ghj.rest.model.GroupMessageToUser;
import com.ghj.rest.model.Message;
import com.ghj.rest.service.GroupMessageService;
import com.ghj.rest.service.GroupMessageToUserService;
import com.ghj.rest.service.MessageService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Component
@RabbitListener(queues = Constant.QUEUE_A)
public class RabbitMqReceiver {

    @Resource
    MessageService messageService;

    @Resource
    GroupMessageToUserService groupMessageToUserService;

    @Resource
    GroupMessageService groupMessageService;

    @RabbitHandler
    public void process(String content) {
        try {
            PersistentMessage message = JSONUtil.toBean(content, PersistentMessage.class);
            if (Objects.isNull(message.getToGroupId())) {
                Message nativeMessage = new Message();
                nativeMessage.setId(message.getId());
                nativeMessage.setFromUserId(message.getFromUserId());
                nativeMessage.setMessageTypeId(nativeMessage.getMessageTypeId());
                nativeMessage.setPostMessage(message.getPostMessage());
                nativeMessage.setSendTime(new Date());
                nativeMessage.setToUserId(message.getToUserId());
                messageService.insertMessage(nativeMessage);
            } else {
                GroupMessage groupMessage = new GroupMessage();
                groupMessage.setId(message.getId());
                groupMessage.setFromUserId( message.getFromUserId());
                groupMessage.setContent(message.getPostMessage());
                groupMessage.setSendTime(new Date());
                groupMessageService.insert(groupMessage);
                message.getOnLineUserIds().forEach(id -> {
                    GroupMessageToUser groupMessageToUser = buildGroupMessageToUser(message, id, true);
                    groupMessageToUserService.insert(groupMessageToUser);
                });
                message.getOffLineUserIds().forEach(id -> {
                    GroupMessageToUser groupMessageToUser = buildGroupMessageToUser(message, id, false);
                    groupMessageToUserService.insert(groupMessageToUser);
                });
            }

        } catch (Exception e) {

        }
    }

    public GroupMessageToUser buildGroupMessageToUser(PersistentMessage message, Integer id, boolean isReceive) {
        GroupMessageToUser groupMessageToUser = new GroupMessageToUser();
        groupMessageToUser.setGroupMessageId(message.getId());
        groupMessageToUser.setSate(isReceive);
        groupMessageToUser.setUserId(id);
        groupMessageToUser.setSendTime(message.getSendTime());
        return groupMessageToUser;
    }
}

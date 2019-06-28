package com.ghj.chat;

import com.ghj.common.dto.AbstractMessage;
import com.ghj.common.dto.MessageToGroup;
import com.ghj.common.dto.MessageToUser;
import com.ghj.common.exception.ChatException;
import com.ghj.common.mq.SendUtil;


/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class MessageSender implements Runnable {

    public MessageProto.message message;

    Integer sessionKey;

    Session session;

    AbstractMessage abstractMessage;

    public MessageSender(MessageProto.message message) {
        this.message = message;
    }

    @Override
    public void run() {
        switch (message.getMessageDirect()) {
            case PERSONAL:
                sessionKey = message.getToUserId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(message);
                abstractMessage = MessageToUser.builder().build();
                break;
            case GROUP:
                sessionKey = message.getToGroupId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(message);
                abstractMessage = MessageToGroup.builder().build();
                break;
                default:
        }
        SendUtil.sendForQueue(abstractMessage);

    }
}

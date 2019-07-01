package com.ghj.chat.message;

import com.ghj.chat.Session;
import com.ghj.chat.SessionManager;
import com.ghj.common.dto.AbstractMessage;
import com.ghj.common.dto.MessageToUser;
import com.ghj.common.exception.ChatException;
import com.ghj.common.mq.SendUtil;
import com.ghj.protocol.AckMessageProto;

public class AckMessageSender implements Runnable{

    public AckMessageProto.AckMessage message;

    Session session;

    AbstractMessage abstractMessage;

    public AckMessageSender(AckMessageProto.AckMessage message) {
        this.message = message;
    }

    @Override
    public void run() {
        Integer sessionKey = message.getToUserId();
        session = SessionManager.getSession(sessionKey);
        if (session == null) {
            throw new ChatException();
        }
        session.getChannel().writeAndFlush(message);
        abstractMessage = MessageToUser.builder().build();
        SendUtil.sendForQueue(abstractMessage);

    }
}

package com.ghj.chat;

import com.ghj.common.ChatException;
import com.ghj.common.protocol.MessageProto;

/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class MessageSender implements Runnable {

    public MessageProto messageProto;

    Integer sessionKey;

    Session session;

    public MessageSender(MessageProto messageProto) {
        this.messageProto = messageProto;
    }

    @Override
    public void run() {
        switch (messageProto.getMessageDirect()) {
            case PERSONAL:
                sessionKey = messageProto.getToUserId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(messageProto);
                break;
            case GROUP:
                sessionKey = messageProto.getToGroupId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(messageProto);
                break;
                default:
        }

    }
}

package com.ghj.chat;

import com.ghj.common.ChatException;
import com.ghj.common.protocol.MessageProto;

import static com.ghj.common.MessageDirect.GROUP;
import static com.ghj.common.MessageDirect.PERSONAL;

/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class MessageSender implements Runnable {

    public MessageProto.message message;

    Integer sessionKey;

    Session session;

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
                break;
            case GROUP:
                sessionKey = message.getToGroupId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(message);
                break;
                default:
        }

    }
}

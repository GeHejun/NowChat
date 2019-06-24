package com.ghj.chat;

import com.ghj.common.ChatException;
import com.ghj.common.protocol.MessageProto;

/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class MessageSender implements Runnable {

    public MessageProto messageProto;

    public MessageSender(MessageProto messageProto) {
        this.messageProto = messageProto;
    }

    @Override
    public void run() {
        switch (messageProto.getClientBehavior()) {
            case MESSAGE:
                Integer toUserId = messageProto.getToUserId();
                Session toSession = SessionManager.getSession(toUserId);
                if (toSession == null) {
                    throw new ChatException();
                }
                toSession.getChannel().writeAndFlush(messageProto);
                break;
            case PING:
                Integer fromUserId = messageProto.getFromUserId();
                Session fromSession = SessionManager.getSession(fromUserId);
                if (fromSession == null) {
                    throw new ChatException();
                }
                fromSession.getChannel().writeAndFlush(messageProto);
                break;
                default:
        }

    }
}

package com.ghj.chat;

import com.ghj.chat.protocol.RequestMessageProto;
import com.ghj.common.dto.AbstractMessage;
import com.ghj.common.dto.MessageToGroup;
import com.ghj.common.dto.MessageToUser;
import com.ghj.common.exception.ChatException;
import com.ghj.common.mq.SendUtil;
import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.OKHttpUtil;

import java.util.List;


/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class MessageSender implements Runnable {

    public RequestMessageProto.RequestMessage message;



    Session session;

    AbstractMessage abstractMessage;

    public MessageSender(RequestMessageProto.RequestMessage message) {
        this.message = message;
    }

    @Override
    public void run() {
        switch (message.getMessageDirect()) {
            case PERSONAL:
                Integer sessionKey = message.getToUserId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(message);
                abstractMessage = MessageToUser.builder().build();
                break;
            case GROUP:
                Integer toGroupId= message.getToGroupId();
                String result = OKHttpUtil.get(Route.GET_GROUP_MEMBER + toGroupId);
                List<Integer> toIds = (List<Integer>)JSONUtil.jsonToList(result);
                toIds.remove(message.getFromUserId());
                toIds.forEach(id -> {
                    session = SessionManager.getSession(id);
                    if (session == null) {
                        throw new ChatException();
                    }
                    session.getChannel().writeAndFlush(message);
                });
                abstractMessage = MessageToGroup.builder().build();
                break;
                default:
        }
        SendUtil.sendForQueue(abstractMessage);

    }
}

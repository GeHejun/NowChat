package com.ghj.chat.message;

import com.ghj.chat.Session;
import com.ghj.chat.SessionManager;
import com.ghj.chat.constant.Route;
import com.ghj.common.base.Code;
import com.ghj.common.dto.AbstractMessage;
import com.ghj.common.dto.MessageToGroup;
import com.ghj.common.dto.MessageToUser;
import com.ghj.common.exception.ChatException;
import com.ghj.common.mq.SendUtil;
import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.OKHttpUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.MessageProto;
import com.ghj.protocol.RequestMessageProto;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;



/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class RequestMessageSender implements Runnable {

    public MessageProto.Message message;



    Session session;

    AbstractMessage abstractMessage;

    public RequestMessageSender(MessageProto.Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        switch (message.getMessageDirect()) {
            case PERSONAL:
                Integer sessionKey = message.getToUserId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    //保存离线消息
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(message);
                abstractMessage = MessageToUser.builder().build();
                break;
            case GROUP:
                Integer toGroupId= message.getToGroupId();
                OKHttpUtil.get(Route.GET_GROUP_MEMBER + toGroupId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        MessageProto.Message ackMessage = MessageProto.Message.newBuilder().setCode(Code.GROUP_MEMBER_REQUEST_FAILURE.getCode())
                                .setContent(Code.GROUP_MEMBER_REQUEST_FAILURE.getMessage())
                                .setMatchMessageId(message.getId())
                                .setToUserId(message.getFromUserId())
                                .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId())
                                .build();
                        MessageManager.getInstance().ackMessageQueue(ackMessage);
                    }
                    @Override
                    public void onResponse(Call call, Response response) {
                        String result = response.body().toString();
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
                    }
                });
                break;
            case SERVER:
                MessageManager.getInstance().ackMessageQueue(buildAckMessage(Code.ACK_SEND_SUCCESS, true, message));
                MessageManager.getInstance().ackMessageQueue(buildAckMessage(Code.MESSAGE_RECEIVER_SUCCESS, false, message));
                default:
        }
        SendUtil.sendForQueue(abstractMessage);

    }

    public MessageProto.Message buildAckMessage(Code code, boolean isAckSender, MessageProto.Message message) {
        return MessageProto.Message.newBuilder()
                .setCode(code.getCode())
                .setContent(code.getMessage())
                .setId(new SnowFlakeIdGenerator(message.getDeviceId(), message.getMachineSerialNumber()).nextId())
                .setToUserId(isAckSender ? message.getFromUserId() : message.getToUserId())
                .setAssociatedGroupId(message.getAssociatedGroupId())
                .setMatchMessageId(message.getId())
                .build();
    }
}

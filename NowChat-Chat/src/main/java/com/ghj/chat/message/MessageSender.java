package com.ghj.chat.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ghj.chat.Session;
import com.ghj.chat.SessionManager;
import com.ghj.chat.constant.Route;
import com.ghj.common.base.Code;
import com.ghj.common.dto.PersistentMessage;
import com.ghj.common.exception.ChatException;
import com.ghj.common.util.MachineSerialNumber;
import com.ghj.common.util.OKHttpUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.MessageProto;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

import static com.ghj.common.base.Constant.DATA_KEY;
import static com.ghj.protocol.MessageProto.Message.MessageBehavior.ACK;


/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class MessageSender implements Runnable {

    public MessageProto.Message message;

    Session session;

    PersistentMessage persistentMessage;

    public MessageSender(MessageProto.Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        if (ACK == message.getMessageBehavior()) {
            Integer sessionKey = message.getToUserId();
            session = SessionManager.getSession(sessionKey);
            if (session == null) {
                throw new ChatException();
            }
            session.getChannel().writeAndFlush(message);
        } else {
            switch (message.getMessageDirect()) {
                case PERSONAL:
                    Integer sessionKey = message.getToUserId();
                    session = SessionManager.getSession(sessionKey);
                    if (session == null) {
                        //保存离线消息
                        throw new ChatException();
                    }
                    session.getChannel().writeAndFlush(message);
                    //persistentMessage = PersistentMessage.builder().id(message.getId()).build();
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
                                    .setId(new SnowFlakeIdGenerator(message.getDeviceId(), MachineSerialNumber.get()).nextId())
                                    .setMessageBehavior(ACK)
                                    .build();
                            MessageManager.getInstance().putMessage(ackMessage);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            JSONObject jsonObject = (JSONObject)JSON.parse(result);
                            JSONArray toIds = jsonObject.getJSONArray(DATA_KEY);
                            toIds.forEach(id -> {
                                if (!Objects.equals(id, message.getFromUserId())) {
                                    session = SessionManager.getSession(Integer.parseInt(id.toString()));
                                    if (session == null) {
                                        throw new ChatException();
                                    }
                                    session.getChannel().writeAndFlush(message);
                                }
                            });
                            persistentMessage = PersistentMessage.builder().build();
                        }
                    });
                    break;
                case SERVER:
                    MessageManager.getInstance().putMessage(buildAckMessage(Code.ACK_SEND_SUCCESS, true, message));
                    MessageManager.getInstance().putMessage(buildAckMessage(Code.MESSAGE_RECEIVER_SUCCESS, false, message));
                default:
            }
        }
        //SendUtil.sendForQueue(abstractMessage);
    }

    public MessageProto.Message buildAckMessage(Code code, boolean isAckSender, MessageProto.Message message) {
        return MessageProto.Message.newBuilder()
                .setCode(code.getCode())
                .setContent(code.getMessage())
                .setId(new SnowFlakeIdGenerator(message.getDeviceId(), MachineSerialNumber.get()).nextId())
                .setToUserId(isAckSender ? message.getFromUserId() : message.getToUserId())
                .setAssociatedGroupId(message.getAssociatedGroupId())
                .setMatchMessageId(message.getId())
                .build();
    }
}

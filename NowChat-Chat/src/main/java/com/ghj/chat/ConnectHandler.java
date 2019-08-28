package com.ghj.chat;

import com.ghj.chat.message.MessageManager;
import com.ghj.common.base.Code;
import com.ghj.common.base.Constant;
import com.ghj.common.exception.UserException;
import com.ghj.common.util.*;
import com.ghj.protocol.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
@ChannelHandler.Sharable
public class ConnectHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        System.out.println(o);
        MessageProto.Message message = (MessageProto.Message)o;
        Channel channel = channelHandlerContext.channel();
        MessageProto.Message ackMessage;
        switch (message.getMessageBehavior()) {
            case LOGIN:
                try {
                    validateUser(message);
                    NettyAttrUtil.updateReaderTime(channel, System.currentTimeMillis() + Constant.PING_ADD_TIME);
                    Session session = Session.builder().channel(channel)
                            .userId(message.getFromUserId())
                            .build();
                    SessionManager.putSession(message.getFromUserId(), session);
                    incrementOnLineUser(message);
                    ackMessage = buildAckMessage(Code.LOGIN_SUCCESS, message);
                } catch (Exception e) {
                    ackMessage = buildAckMessage(Code.LOGIN_FAILURE, message);
                }
                MessageManager.getInstance().putMessage(ackMessage);
                break;
            case PING:
                try {
                    NettyAttrUtil.updateReaderTime(channel, System.currentTimeMillis() + Constant.PING_ADD_TIME);
                    MessageManager.getInstance().putMessage(message);
                    ackMessage = buildAckMessage(Code.PING_SUCCESS, message);
                } catch (Exception e) {
                    ackMessage = buildAckMessage(Code.PING_FAILURE, message);
                }
                MessageManager.getInstance().putMessage(ackMessage);
                break;
            case MESSAGE:
                try {
                    MessageManager.getInstance().putMessage(message);
                    ackMessage = buildAckMessage(Code.MESSAGE_SEND_SUCCESS, message);
                } catch (Exception e) {
                    ackMessage = buildAckMessage(Code.MESSAGE_SEND_FAILURE, message);
                }
                MessageManager.getInstance().putMessage(ackMessage);
                break;
            case LOGIN_OUT:
                try {
                    SessionManager.removeSession(message.getFromUserId());
                    ackMessage = buildAckMessage(Code.LOGIN_OUT_SUCCESS, message);
                } catch (Exception e) {
                    ackMessage = buildAckMessage(Code.LOGIN_OUT_FAILURE, message);
                }
                MessageManager.getInstance().putMessage(ackMessage);
                break;
            case ACK:
                break;
            case UNRECOGNIZED:
                default:
        }
    }

    public void validateUser(MessageProto.Message message) {
        String token = RedisPoolUtil.get(Constant.SYSTEM_PREFIX + message.getFromUserId() + "_" + Constant.USER_TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            throw new UserException();
        }
        if (!token.equals(message.getToken())) {
            throw new UserException();
        }

    }

    public synchronized void incrementOnLineUser(MessageProto.Message message) {
        RedisPoolUtil.lpush(Constant.ON_LINE_USER_LIST, String.valueOf(message.getFromUserId()));
        RedisPoolUtil.increment(Constant.ON_LINE_USER_COUNT);
    }

    public MessageProto.Message buildAckMessage(Code code, MessageProto.Message message) {
        return MessageProto.Message.newBuilder()
                .setCode(code.getCode())
                .setContent(code.getMessage())
                .setId(new SnowFlakeIdGenerator(message.getDeviceId(), MachineSerialNumber.get()).nextId())
                .setToUserId(message.getFromUserId())
                .setMatchMessageId(message.getId())
                .setMessageBehavior(MessageProto.Message.MessageBehavior.ACK)
                .build();
    }



}

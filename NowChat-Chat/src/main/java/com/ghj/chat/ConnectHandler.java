package com.ghj.chat;

import com.ghj.chat.message.MessageManager;
import com.ghj.chat.protocol.AckMessageProto;
import com.ghj.chat.protocol.RequestMessageProto;
import com.ghj.common.base.Code;
import com.ghj.common.base.Constant;
import com.ghj.common.exception.UserException;
import com.ghj.common.util.NettyAttrUtil;
import com.ghj.common.util.RedisPoolUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.common.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class ConnectHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        RequestMessageProto.RequestMessage message = (RequestMessageProto.RequestMessage)o;
        Channel channel = channelHandlerContext.channel();
        AckMessageProto.AckMessage ackMessage;
        switch (message.getClientBehavior()) {
            case LOGIN:
                try {
                    validateUser(message);
                    Session session = Session.builder().channel(channel)
                            .userId(message.getFromUserId())
                            .loginName(message.getLoginName())
                            .nickName(message.getNickName())
                            .build();
                    SessionManager.putSession(message.getFromUserId(), session);
                    incrementOnLineUser(message);
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.LOGIN_SUCCESS.getCode())
                            .setContent(Code.LOGIN_SUCCESS.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                } catch (Exception e) {
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.LOGIN_FAILURE.getCode())
                            .setContent(Code.LOGIN_FAILURE.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                }
                MessageManager.getInstance().ackMessageQueue(ackMessage);
                break;
            case PING:
                try {
                    NettyAttrUtil.updateReaderTime(channel, Constant.PING_ADD_TIME);
                    MessageManager.getInstance().putMessage(message);
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.PING_SUCCESS.getCode())
                            .setContent(Code.PING_SUCCESS.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                } catch (Exception e) {
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.PING_FAILURE.getCode())
                            .setContent(Code.PING_FAILURE.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                }
                channel.writeAndFlush(ackMessage);
                break;
            case MESSAGE:
                try {
                    MessageManager.getInstance().putMessage(message);
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.MESSAGE_SEND_SUCCESS.getCode())
                            .setContent(Code.MESSAGE_SEND_SUCCESS.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                } catch (Exception e) {
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.MESSAGE_SEND_FAILURE.getCode())
                            .setContent(Code.MESSAGE_SEND_FAILURE.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                }
                channel.writeAndFlush(ackMessage);
                break;
            case LOGIN_OUT:
                try {
                    SessionManager.removeSession(message.getFromUserId());
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.LOGIN_OUT_SUCCESS.getCode())
                            .setContent(Code.LOGIN_OUT_SUCCESS.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                } catch (Exception e) {
                    ackMessage = AckMessageProto.AckMessage.newBuilder()
                            .setCode(Code.LOGIN_OUT_FAILURE.getCode())
                            .setContent(Code.LOGIN_OUT_FAILURE.getMessage())
                            .setId(new SnowFlakeIdGenerator(message.getDeviceId(), 29L).nextId()).build();
                }
                channel.writeAndFlush(ackMessage);
                break;
            case UNRECOGNIZED:
                default:
        }
    }

    public void validateUser(RequestMessageProto.RequestMessage message) {
        String token = RedisPoolUtil.get(message.getFromUserId()+"_"+Constant.USER_TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            throw new UserException();
        }
        if (!token.equals(message.getToken())) {
            throw new UserException();
        }

    }

    public synchronized void incrementOnLineUser(RequestMessageProto.RequestMessage message) {
        RedisPoolUtil.lpush(Constant.ON_LINE_USER_LIST, String.valueOf(message.getFromUserId()));
        RedisPoolUtil.increment(Constant.ON_LINE_USER_COUNT);
    }

}

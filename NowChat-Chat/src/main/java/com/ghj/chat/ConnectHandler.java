package com.ghj.chat;

import com.ghj.common.base.Constant;
import com.ghj.common.exception.UserException;
import com.ghj.common.mq.SendUtil;
import com.ghj.common.protocol.MessageProto;
import com.ghj.common.util.NettyAttrUtil;
import com.ghj.common.util.RedisPool;
import com.ghj.common.util.RedisPoolUtil;
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
        MessageProto.message message = (MessageProto.message)o;
        Channel channel = channelHandlerContext.channel();
        switch (message.getClientBehavior()) {
            case LOGIN:
                validateUser(message);
                Session session = Session.builder().channel(channel).build();
                SessionManager.putSession(message.getFromUserId(), session);
                break;
            case PING:
                NettyAttrUtil.updateReaderTime(channel, 10000L);
                MessageManager.getInstance().putMessage(message);
                break;
            case MESSAGE:
                MessageManager.getInstance().putMessage(message);
                SendUtil.sendForQueue(message);
                break;
            case LOGIN_OUT:
                break;
            case UNRECOGNIZED:
                default:
        }
    }

    public void validateUser(MessageProto.message message) {
        String token = RedisPoolUtil.get(message.getFromUserId()+"_"+Constant.USER_TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            throw new UserException();
        }
        if (!token.equals(message.getToken())) {
            throw new UserException();
        }
        incrementOnLineUser(message);
    }

    public synchronized void incrementOnLineUser(MessageProto.message message) {
        RedisPoolUtil.lpush(Constant.ON_LINE_USER_LIST, String.valueOf(message.getFromUserId()));
        RedisPoolUtil.increment(Constant.ON_LINE_USER_COUNT);
    }
}

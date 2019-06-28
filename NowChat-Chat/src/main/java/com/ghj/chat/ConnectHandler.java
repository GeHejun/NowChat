package com.ghj.chat;

import com.ghj.chat.protocol.RequestMessageProto;
import com.ghj.common.base.Constant;
import com.ghj.common.exception.UserException;
import com.ghj.common.util.NettyAttrUtil;
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
        RequestMessageProto.RequestMessage message = (RequestMessageProto.RequestMessage)o;
        Channel channel = channelHandlerContext.channel();
        switch (message.getClientBehavior()) {
            case LOGIN:
                validateUser(message);
                Session session = Session.builder().channel(channel)
                        .userId(message.getFromUserId())
                        .loginName(message.getLoginName())
                        .nickName(message.getNickName())
                        .build();
                SessionManager.putSession(message.getFromUserId(), session);
                incrementOnLineUser(message);
//                channel.writeAndFlush()
                break;
            case PING:
                NettyAttrUtil.updateReaderTime(channel, Constant.PING_ADD_TIME);
                MessageManager.getInstance().putMessage(message);
                break;
            case MESSAGE:
                MessageManager.getInstance().putMessage(message);
                break;
            case LOGIN_OUT:
                SessionManager.removeSession(message.getFromUserId());
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

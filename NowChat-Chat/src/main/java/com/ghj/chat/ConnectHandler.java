package com.ghj.chat;

import com.ghj.common.ChatException;
import com.ghj.common.NettyAttrUtil;
import com.ghj.common.mq.SendUtil;
import com.ghj.common.protocol.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        MessageProto messageProto = (MessageProto)o;
        Channel channel = channelHandlerContext.channel();
        switch (messageProto.getClientBehavior()) {
            case LOGIN:
                Session session = Session.builder().channel(channel).build();
                SessionManager.putSession(messageProto.getFromUserId(), session);
                break;
            case PING:
                NettyAttrUtil.updateReaderTime(channel, 10000L);
                break;
            case MESSAGE:
                Integer toUserId = messageProto.getToUserId();
                Session toSession = SessionManager.getSession(toUserId);
                if (toSession == null) {
                    throw new ChatException();
                }
                toSession.getChannel().writeAndFlush(o);
                SendUtil.sendForQueue(messageProto);
                break;
                default:
        }
    }
}

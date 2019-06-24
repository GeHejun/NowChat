package com.ghj.chat;

import com.ghj.common.NettyAttrUtil;
import com.ghj.common.mq.SendUtil;
import com.ghj.common.protocol.MessageProto;
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
        MessageProto messageProto = (MessageProto)o;
        Channel channel = channelHandlerContext.channel();
        switch (messageProto.getClientBehavior()) {
            case LOGIN:
                Session session = Session.builder().channel(channel).build();
                SessionManager.putSession(messageProto.getFromUserId(), session);
                break;
            case PING:
                NettyAttrUtil.updateReaderTime(channel, 10000L);
                MessageManager.getInstance().putMessage(messageProto);
                break;
            case MESSAGE:
                MessageManager.getInstance().putMessage(messageProto);
                SendUtil.sendForQueue(messageProto);
                break;
                default:
        }
    }
}

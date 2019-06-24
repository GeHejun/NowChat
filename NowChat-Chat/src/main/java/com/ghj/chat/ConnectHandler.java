package com.ghj.chat;

import com.ghj.common.NettyAttrUtil;
import com.ghj.common.mq.SendUtil;
import com.ghj.common.protocol.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.ghj.common.ClientBehavior.LOGIN;
import static com.ghj.common.ClientBehavior.PING;
import static com.ghj.common.protocol.MessageProto.message.ClientBehavior.MESSAGE;

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
                default:
        }
    }
}

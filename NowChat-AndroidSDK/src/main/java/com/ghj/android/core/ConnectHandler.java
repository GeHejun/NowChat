package com.ghj.android.core;

import com.ghj.android.message.RequestMessageListener;
import com.ghj.android.message.AckMessageListener;
import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gehj
 * @date 2019/7/415:03
 */
public class ConnectHandler extends SimpleChannelInboundHandler {

    AckMessageListener ackMessageListener;

    RequestMessageListener requestMessageListener;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)  {
        if (msg instanceof AckMessageProto.AckMessage) {
            AckMessageProto.AckMessage ackMessage = (AckMessageProto.AckMessage) msg;
           ackMessageListener.dealAckMessage(ackMessage);

        }
        if (msg instanceof RequestMessageProto.RequestMessage) {
            RequestMessageProto.RequestMessage requestMessage = (RequestMessageProto.RequestMessage) msg;
            requestMessageListener.dealRequestMessage(requestMessage);

        }
    }
}

package com.ghj.android.core;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gehj
 * @date 2019/7/415:03
 */
public class ConnectHandler extends SimpleChannelInboundHandler {

    Subject subject;

    public ConnectHandler(Subject subject) {
        this.subject = subject;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)  {
        if (msg instanceof AckMessageProto.AckMessage) {
            AckMessageProto.AckMessage ackMessage = (AckMessageProto.AckMessage) msg;
            subject.ackNotifyAllListener(ackMessage);
        }
        if (msg instanceof RequestMessageProto.RequestMessage) {
            RequestMessageProto.RequestMessage requestMessage = (RequestMessageProto.RequestMessage) msg;
            subject.requestNotifyAllListener(requestMessage);
        }
    }
}

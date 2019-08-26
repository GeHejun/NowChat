package com.ghj.android.core;


import com.ghj.android.core.observer.Subject;
import com.ghj.protocol.MessageProto;
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        MessageProto.Message message = (MessageProto.Message) o;
        subject.notifyAllListener(message);
    }


}

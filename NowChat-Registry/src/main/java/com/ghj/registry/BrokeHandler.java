package com.ghj.registry;


import com.ghj.protocol.RegisterMessageProto.RegisterMessage;
import com.ghj.protocol.RequestMessageProto.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gehj
 * @date 2019/7/115:44
 */
public class BrokeHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        if (o instanceof RegisterMessage) {

        }
        if (o instanceof RequestMessage) {

        }
    }
}

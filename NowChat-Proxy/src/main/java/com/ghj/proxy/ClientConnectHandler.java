package com.ghj.proxy;

import com.ghj.common.netty.BootstrapGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.Objects;

import static com.ghj.protocol.MessageProto.Message.MessageBehavior.ACK;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 12:55
 */
public class ClientConnectHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        MessageProto.Message message = (MessageProto.Message) o;
        if (ACK == message.getMessageBehavior()) {
            long matchMessageId = message.getMatchMessageId();
            MessageProto.Message loginMessage = MessageManager.ROUTE_MESSAGE_MAP.get(matchMessageId);
            if (!Objects.isNull(loginMessage)) {
                channelHandlerContext.channel().close();
                Bootstrap client = BootstrapGenerator.generateBootStrap(new ClientConnectHandler());
                ChannelFuture future = client.connect(new InetSocketAddress(message.getIp(), message.getPort()));
                if (!future.isSuccess()) {

                } else {
                    future.channel().writeAndFlush(loginMessage);
                }
            }
        }
    }
}

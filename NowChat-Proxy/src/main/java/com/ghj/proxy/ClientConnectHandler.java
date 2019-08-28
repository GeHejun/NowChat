package com.ghj.proxy;

import com.ghj.common.netty.BootstrapGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;

import java.net.InetSocketAddress;
import java.util.Objects;

import static com.ghj.protocol.MessageProto.Message.MessageBehavior.ACK;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 12:55
 */
@ChannelHandler.Sharable
public class ClientConnectHandler extends SimpleChannelInboundHandler {
    Channel channel;

    public ClientConnectHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        System.out.println("代理-client-接收信息:" + o);
        MessageProto.Message message = (MessageProto.Message) o;
        long matchMessageId = message.getMatchMessageId();
        MessageProto.Message loginMessage = MessageManager.ROUTE_MESSAGE_MAP.get(matchMessageId);
        if (!Objects.isNull(loginMessage)) {
            channelHandlerContext.channel().close();
            Bootstrap client = BootstrapGenerator.generateBootStrap(new ClientConnectHandler(channel));
            ChannelFuture future = client.connect(new InetSocketAddress(message.getIp(), message.getPort()));
//            if (!future.isSuccess()) {
//
//            }
            System.out.println("登录--客户端---proxy---server---channel即将绑定" + channel + "---" + future.channel());
            SessionManager.bind(channel, future.channel());
            System.out.println("登录--proxyClient---server---channel绑定结束");
            future.channel().writeAndFlush(loginMessage);
            System.out.println("登录--发消息结束");
        } else {
            System.out.println("非登陆消息即将转发");
            SessionManager.getClient(channelHandlerContext.channel()).writeAndFlush(message);
            System.out.println("非登陆消息转发完毕");
        }
    }
}

package com.ghj.proxy;

import com.ghj.common.base.Constant;
import com.ghj.common.netty.BootstrapGenerator;
import com.ghj.common.util.MachineSerialNumber;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;

import java.net.InetSocketAddress;

import static com.ghj.protocol.MessageProto.Message.MessageBehavior.*;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 12:59
 */
@ChannelHandler.Sharable
public class ConnectHandler extends SimpleChannelInboundHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("proxy-serverHandler-active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        MessageProto.Message message = (MessageProto.Message) o;
        if (LOGIN == message.getMessageBehavior()) {
            System.out.println(channelHandlerContext.channel().toString());
            route(channelHandlerContext.channel(), message);
            System.out.println("-----------");
        } else if (LOGIN_OUT == message.getMessageBehavior()) {

        } else {
            SessionManager.getServer(channelHandlerContext.channel()).writeAndFlush(message);
        }
    }

    private void route(Channel channel,  MessageProto.Message message) {
        System.out.println(channel.toString());
        Bootstrap client = BootstrapGenerator.generateBootStrap(new ClientConnectHandler(channel));
        String registryIp = PropertiesUtil.getInstance().getValue(Constant.REGISTRY_IP, "127.0.0.1");
        int port = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTRY_PORT, "9999"));
        ChannelFuture channelFuture = client.connect(new InetSocketAddress(registryIp, port));
        if (!channelFuture.isSuccess()) {

        } else {
            channelFuture.channel().writeAndFlush(buildRoutMessage(message));
        }
    }

    private MessageProto.Message buildRoutMessage(MessageProto.Message message) {
        Long id = new SnowFlakeIdGenerator(MachineSerialNumber.get(), MachineSerialNumber.get()).nextId();
        MessageManager.putMessageRoute(id, message);
        return MessageProto.Message.newBuilder()
                .setConnectType(MessageProto.Message.ConnectType.NETTY)
                .setMessageBehavior(PROXY_ROUTE)
                .setId(id)
                .build();
    }
}

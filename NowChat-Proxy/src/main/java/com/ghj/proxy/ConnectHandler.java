package com.ghj.proxy;

import com.ghj.common.base.Constant;
import com.ghj.common.netty.BootstrapGenerator;
import com.ghj.common.util.MachineSerialNumber;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.MessageProto;
import com.ghj.proxy.ClientConnectHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

import static com.ghj.protocol.MessageProto.Message.MessageBehavior.*;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 12:59
 */
public class ConnectHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        MessageProto.Message message = (MessageProto.Message) o;
        if (LOGIN == message.getMessageBehavior()) {
            route(message);
        } else if (LOGIN_OUT == message.getMessageBehavior()) {

        } else {

        }
    }

    private void route(MessageProto.Message message) {
        Bootstrap client = BootstrapGenerator.generateBootStrap(new ClientConnectHandler());
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
        MessageProto.Message routeMessage = MessageProto.Message.newBuilder()
                .setConnectType(MessageProto.Message.ConnectType.NETTY)
                .setMessageBehavior(PROXY_ROUTE)
                .setId(id)
                .build();
        return routeMessage;
    }
}

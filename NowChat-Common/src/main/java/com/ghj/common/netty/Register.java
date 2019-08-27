package com.ghj.common.netty;

import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.exception.ServerException;
import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @author gehj
 * @version 1.0
 * @description 注册器
 * @date 2019/8/27 13:02
 */
public class Register {

    private static volatile int RETRY_COUNT = 3;
    private static String registerIp = PropertiesUtil.getInstance().getValue(Constant.REGISTRY_IP, "127.0.0.1");
    ChannelFuture channelFuture;

    public ChannelFuture register(Connector connector, MessageProto.Message.ConnectType connectType, MessageProto.Message.MessageBehavior messageBehavior) {
        register(connector, connectType, messageBehavior, BootstrapGenerator.generateBootStrap(new SimpleChannelInboundHandler() {
            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
                RETRY_COUNT--;
                if (RETRY_COUNT > 0 && Objects.isNull(o)) {
                    reRegister(registerIp, connector, connectType, messageBehavior, channelFuture);
                }
                MessageProto.Message message = (MessageProto.Message) o;
                Result result = (Result) JSONUtil.jsonToBean(message.getContent(), Result.class);
                if (RETRY_COUNT > 0 &&  Constant.REGISTER_SUCCESS_CODE != result.getCode()) {
                    reRegister(registerIp, connector, connectType, messageBehavior, channelFuture);
                }
            }
        }));
        return channelFuture;
    }

    private void register(Connector connector, MessageProto.Message.ConnectType connectType, MessageProto.Message.MessageBehavior messageBehavior, Bootstrap client) {
        try {
            Integer registerPort = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTRY_PORT, "6798"));
            channelFuture = client.connect((new InetSocketAddress(registerIp, registerPort)));
            channelFuture.addListener(future -> {
                if (!future.isSuccess()) {
                    connector.stop();
                    throw new ServerException();
                }
                InetSocketAddress inetSocketAddress = (InetSocketAddress) channelFuture.channel().remoteAddress();
                String ip = inetSocketAddress.getAddress().getHostAddress();
                reRegister(ip, connector, connectType, messageBehavior, channelFuture);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reRegister(String ip, Connector connector, MessageProto.Message.ConnectType connectType, MessageProto.Message.MessageBehavior messageBehavior, ChannelFuture channelFuture) {
        MessageProto.Message registerMessage =
                MessageProto.Message.newBuilder()
                        .setIp(ip)
                        .setPort(connector.getPort())
                        .setMessageBehavior(messageBehavior)
                        .setConnectType(connectType)
                        .build();
        channelFuture.channel().writeAndFlush(registerMessage);
    }
}

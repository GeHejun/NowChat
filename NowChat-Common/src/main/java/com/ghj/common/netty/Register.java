package com.ghj.common.netty;

import com.ghj.common.base.Constant;
import com.ghj.common.exception.ServerException;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

/**
 * @author gehj
 * @version 1.0
 * @description 注册器
 * @date 2019/8/27 13:02
 */
public class Register {

    public void register(Connector connector, MessageProto.Message.ConnectType connectType, SimpleChannelInboundHandler handler) {
        register(connector, connectType, BootstrapGenerator.generateBootStrap(handler));
    }

    private void register(Connector connector, MessageProto.Message.ConnectType connectType, Bootstrap client) {
        try {
            String registerIp = PropertiesUtil.getInstance().getValue(Constant.REGISTRY_IP, "127.0.0.1");
            Integer registerPort = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTRY_PORT, "6798"));
            ChannelFuture channelFuture = client.connect((new InetSocketAddress(registerIp, registerPort)));
            channelFuture.addListener(future -> {
                if (!future.isSuccess()) {
                    connector.stop();
                    throw new ServerException();
                }
                InetSocketAddress inetSocketAddress = (InetSocketAddress) channelFuture.channel().remoteAddress();
                String ip = inetSocketAddress.getAddress().getHostAddress();
                MessageProto.Message registerMessage =
                        MessageProto.Message.newBuilder()
                                .setIp(ip)
                                .setPort(connector.getPort())
                                .setMessageBehavior(MessageProto.Message.MessageBehavior.REGISTER)
                                .setConnectType(connectType)
                                .build();
                channelFuture.channel().writeAndFlush(registerMessage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

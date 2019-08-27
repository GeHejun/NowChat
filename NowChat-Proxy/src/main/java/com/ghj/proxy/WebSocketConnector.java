package com.ghj.proxy;

import com.ghj.common.netty.Connector;
import com.ghj.common.netty.Register;
import com.ghj.common.netty.ServerChannelGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 13:30
 */
public class WebSocketConnector implements Connector {

    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private int port;
    Channel channel;

    @Override
    public void start(int port) {
        this.port = port;
        channel = ServerChannelGenerator.generateChannel(bossGroup, workerGroup, port,
                MessageProto.Message.ConnectType.WEBSOCKET, new ConnectHandler());
        new Register().register(this, MessageProto.Message.ConnectType.WEBSOCKET, MessageProto.Message.MessageBehavior.PROXY_REGISTER);
    }

    @Override
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    @Override
    public int getPort() {
        return port;
    }
}

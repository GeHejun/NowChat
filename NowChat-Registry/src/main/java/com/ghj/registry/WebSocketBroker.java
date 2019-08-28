package com.ghj.registry;

import com.ghj.common.netty.ServerChannelGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author GeHejun
 * @date 2019/7/1 11:22
 */
public class WebSocketBroker {

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public void start(int port) {
        ServerChannelGenerator.generateChannel(bossGroup, workerGroup, port, MessageProto.Message.ConnectType.WEBSOCKET,
                new BrokeHandler(MessageProto.Message.ConnectType.WEBSOCKET), ()->{});
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

package com.ghj.registry;

import com.ghj.common.netty.ServerChannelGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author GeHejun
 */
public class NettyBroker {

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();


    public void start(int port) {
        ServerChannelGenerator.generateChannel(bossGroup, workerGroup, port, MessageProto.Message.ConnectType.NETTY,
                new BrokeHandler(MessageProto.Message.ConnectType.NETTY), ()->{});
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}

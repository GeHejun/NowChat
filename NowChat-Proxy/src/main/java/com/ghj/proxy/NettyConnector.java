package com.ghj.proxy;

import com.ghj.common.netty.Connector;
import com.ghj.common.netty.Register;
import com.ghj.common.netty.ServerChannelGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 11:46
 */
public class NettyConnector implements Connector {

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    int port;

    @Override
    public void start(int port) {
        this.port = port;
        try {
            ServerChannelGenerator.generateChannel(bossGroup, workerGroup, port,
                    MessageProto.Message.ConnectType.NETTY, new ConnectHandler(), ()->{
                        new Register().register(this, MessageProto.Message.ConnectType.NETTY, MessageProto.Message.MessageBehavior.PROXY_REGISTER);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
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

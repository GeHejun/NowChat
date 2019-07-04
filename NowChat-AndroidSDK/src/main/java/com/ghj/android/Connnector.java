package com.ghj.android;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author gehj
 * @date 2019/7/414:52
 */
public class Connnector {

    Bootstrap bootstrap;
    NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

    public void connect(String host, int port) {
        try {
            bootstrap = new Bootstrap()
                    .group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        public void initChannel(SocketChannel ch)
                        {
                            ch.pipeline().addLast(new ConnectHandler());
                        }
                    });
            // 发起异步连接操作
            ChannelFuture f = bootstrap.connect(host, port).sync();
            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
    public void stop() {
        nioEventLoopGroup.shutdownGracefully();
    }
}

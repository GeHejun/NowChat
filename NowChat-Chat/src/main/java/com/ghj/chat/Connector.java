package com.ghj.chat;


import com.ghj.common.base.Constant;
import com.ghj.common.exception.ServerException;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.NotifyMessageProto;
import com.ghj.protocol.RegistryMessageProto;
import com.ghj.protocol.RequestMessageProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class Connector {

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap serverBootstrap;


    public void start(int port) {
        try {
            serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ProtobufDecoder(RequestMessageProto.RequestMessage.getDefaultInstance()));
                            pipeline.addLast(new ProtobufDecoder(AckMessageProto.AckMessage.getDefaultInstance()));
                            pipeline.addLast(new ProtobufDecoder(NotifyMessageProto.NotifyMessage.getDefaultInstance()));
                            pipeline.addLast(new ProtobufDecoder(RegistryMessageProto.RegistryMessage.getDefaultInstance()));
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new ConnectHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            Channel channel = channelFuture.channel();
            register(channel);
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public void register(Channel channel) {
        new Thread(() -> {
            try {
                String registerIp = PropertiesUtil.getInstance().getValue(Constant.REGISTER_IP, "");
                Integer registerPort = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTER_PORT, ""));
                ChannelFuture channelFuture = channel.connect(new InetSocketAddress(registerIp, registerPort));
                channelFuture.addListener(future -> {
                    if (!future.isSuccess()) {
                        throw new ServerException();
                    }
                    RegistryMessageProto.RegistryMessage registerMessage =
                            RegistryMessageProto.RegistryMessage.newBuilder().setMachineSerialNumber(Constant.MACHINE_SERIAL_NUMBER).build();
                    channel.writeAndFlush(registerMessage);
                });
            } catch (Exception e) {

            }

        }).start();
    }
}

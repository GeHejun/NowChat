package com.ghj.android.core;

import com.ghj.android.core.message.MessageManager;
import com.ghj.android.core.observer.Subject;
import com.ghj.common.util.MachineSerialNumber;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author gehj
 * @date 2019/7/414:52
 */
public class Connnector {

    Bootstrap bootstrap;
    NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

    public void connect(String host, int port, MessageManager messageManager, Subject subject) {
        try {
            bootstrap = new Bootstrap()
                    .group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
                            ch.pipeline().addLast(new ConnectHandler(subject));
                        }
                    });
            // 发起异步连接操作
            ChannelFuture f = bootstrap.connect(host, port).sync();
            Channel channel = f.channel();
            f.addListener(future -> {
                if (future.isSuccess()) {
                    messageManager.setChannel(channel);
                    MessageProto.Message requestMessage = MessageProto.Message.newBuilder()
                            .setMessageDirect(MessageProto.Message.MessageDirect.SERVER)
                            .setId(new SnowFlakeIdGenerator(MachineSerialNumber.get(), 0L).nextId())
                            .setMessageBehavior(MessageProto.Message.MessageBehavior.LOGIN)
                            .build();
                    messageManager.sendLoginMessage(requestMessage);
                }
            });


            // 等待客户端链路关闭
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
    public void stop() {
        nioEventLoopGroup.shutdownGracefully();
    }
}

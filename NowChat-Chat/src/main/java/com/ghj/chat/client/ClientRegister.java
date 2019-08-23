package com.ghj.chat.client;

import com.ghj.chat.Connector;
import com.ghj.common.base.Constant;
import com.ghj.common.exception.ServerException;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.common.util.ThreadPoolManager;
import com.ghj.protocol.MessageProto;
import com.ghj.protocol.RegisterMessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.net.InetSocketAddress;

/**
 * @author gehj
 * @version 1.0
 * @description 作为客户端去连接注册中心
 * @date 2019/8/22 15:36
 */
public class ClientRegister {

    private Connector connector;

    public void clientStart(Connector connector) {
        this.connector = connector;
        Bootstrap client = new Bootstrap();
        //第1步 定义线程组，处理读写和链接事件，没有了accept事件
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        //第2步 绑定客户端通道
        client.channel(NioSocketChannel.class);

        //第3步 给NIoSocketChannel初始化handler， 处理读写事件
        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                //字符串编码器，一定要加在SimpleClientHandler 的上面
                ch.pipeline().addLast(new ProtobufEncoder());
                ch.pipeline().addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
            }
        });
        register(client);
    }

    public void register(Bootstrap bootstrap) {
        ThreadPoolManager.getsInstance().execute(() -> {
            try {
                String registerIp = PropertiesUtil.getInstance().getValue(Constant.REGISTRY_IP, "");
                Integer registerPort = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTRY_PORT, ""));
                ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(registerIp, registerPort));
                channelFuture.addListener(future -> {
                    if (!future.isSuccess()) {
                        connector.stop();
                        throw new ServerException();
                    }
                    RegisterMessageProto.RegisterMessage registerMessage =
                            RegisterMessageProto.RegisterMessage.newBuilder()
                                    .setIp("127.0.0.1")
                                    .setPort(connector.getPort())
                                    .setMachineSerialNumber(Constant.MACHINE_SERIAL_NUMBER).build();
                    channelFuture.channel().writeAndFlush(registerMessage);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }
}

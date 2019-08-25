package com.ghj.chat.client;

import com.ghj.chat.Connector;
import com.ghj.common.base.Constant;
import com.ghj.common.exception.ServerException;
import com.ghj.common.util.MachineSerialNumber;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.common.util.ThreadPoolManager;
import com.ghj.protocol.MessageProto;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;

/**
 * @author gehj
 * @version 1.0
 * @description 作为客户端去连接注册中心
 * @date 2019/8/22 15:36
 */
public class ClientRegister {

    public void clientStart(Connector connector) {
        register(connector);
    }


    public void register(Connector connector) {
        ThreadPoolManager.getsInstance().execute(() -> {
            try {
                String registerIp = PropertiesUtil.getInstance().getValue(Constant.REGISTRY_IP, "");
                Integer registerPort = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTRY_PORT, ""));
                ChannelFuture channelFuture = connector.getServerBootstrap().bind(new InetSocketAddress(registerIp, registerPort));
                channelFuture.addListener(future -> {
                    if (!future.isSuccess()) {
                        connector.stop();
                        throw new ServerException();
                    }
                    MessageProto.Message registerMessage =
                            MessageProto.Message.newBuilder()
                                    .setIp("127.0.0.1")
                                    .setPort(connector.getPort())
                                    .setMachineSerialNumber(MachineSerialNumber.get())
                                    .setMessageBehavior(MessageProto.Message.MessageBehavior.REGISTER)
                                    .build();

                    channelFuture.channel().writeAndFlush(registerMessage);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}

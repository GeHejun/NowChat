package com.ghj.chat.client;

import com.ghj.common.base.Constant;
import com.ghj.common.exception.ServerException;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.common.util.ThreadPoolManager;
import com.ghj.protocol.RegisterMessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;

/**
 * @author gehj
 * @version 1.0
 * @description 作为客户端去连接注册中心
 * @date 2019/8/22 15:36
 */
public class ClientRegister {

    public void register(Channel channel) {
        ThreadPoolManager.getsInstance().execute(() -> {
            try {
                String registerIp = PropertiesUtil.getInstance().getValue(Constant.REGISTRY_IP, "");
                Integer registerPort = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTRY_PORT, ""));
                ChannelFuture channelFuture = channel.connect(new InetSocketAddress(registerIp, registerPort));
                channelFuture.addListener(future -> {
                    if (!future.isSuccess()) {
                        throw new ServerException();
                    }
                    RegisterMessageProto.RegisterMessage registerMessage =
                            RegisterMessageProto.RegisterMessage.newBuilder()
                                    .setMachineSerialNumber(Constant.MACHINE_SERIAL_NUMBER).build();
                    channel.writeAndFlush(registerMessage);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }
}

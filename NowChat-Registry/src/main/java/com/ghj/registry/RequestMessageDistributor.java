package com.ghj.registry;

import com.ghj.protocol.RequestMessageProto;
import io.netty.channel.Channel;

import java.util.Objects;

/**
 * @author gehj
 * @date 2019/7/314:19
 */
public class RequestMessageDistributor implements Runnable{

    RequestMessageProto.RequestMessage requestMessage;

    public RequestMessageDistributor(RequestMessageProto.RequestMessage requestMessage) {
        this.requestMessage = requestMessage;
    }

    @Override
    public void run() {
        long machineSerialNumber = (requestMessage).getMachineSerialNumber();
        ServerSession serverSessionManager = Registry.getServerSession(machineSerialNumber);
        Channel serverChannel = serverSessionManager.getChannel();
        if (Objects.isNull(serverChannel)) {
            //一致性hash
        }
        serverChannel.writeAndFlush(requestMessage);
    }
}

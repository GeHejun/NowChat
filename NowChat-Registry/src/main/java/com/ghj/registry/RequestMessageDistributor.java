package com.ghj.registry;

import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.channel.Channel;

import java.util.Objects;

/**
 * @author gehj
 * @date 2019/7/314:19
 */
public class RequestMessageDistributor implements Runnable{

    MessageProto.Message requestMessage;

    public RequestMessageDistributor(MessageProto.Message requestMessage) {
        this.requestMessage = requestMessage;
    }

    @Override
    public void run() {
        long machineSerialNumber = requestMessage.getMachineSerialNumber();
        ServerSession serverSessionManager = Registry.getServerSession(machineSerialNumber);
        Channel serverChannel = serverSessionManager.getChannel();
        if (Objects.isNull(serverChannel)) {
            //一致性hash
        }
//        MessageProto.Message message = MessageProto.Message
//                .newBuilder(requestMessage)
//                .setMatchMessageId(new SnowFlakeIdGenerator(requestMessage.getDeviceId(), (long)Math.random() * 10000).nextId()).build();
        serverChannel.writeAndFlush(requestMessage);
    }
}

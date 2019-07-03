package com.ghj.registry;


import com.ghj.common.base.Code;
import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.common.util.ThreadPoolManager;
import com.ghj.protocol.RegisterMessageProto.*;
import com.ghj.protocol.RequestMessageProto.RequestMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author gehj
 * @date 2019/7/115:44
 */
public class BrokeHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        if (o instanceof RegisterMessage) {
            ThreadPoolManager.getsInstance().execute(() -> {
                RegisterMessage registerMessage = (RegisterMessage) o;
                dealRegisterMessage(channelHandlerContext, registerMessage);
            });
        }
        if (o instanceof RequestMessage) {
            ThreadPoolManager.getsInstance().execute(() -> {
                RequestMessage requestMessage = (RequestMessage)o;
                dealRequestMessage(requestMessage);
            });
        }
    }

    public void dealRegisterMessage(ChannelHandlerContext channelHandlerContext, RegisterMessage registerMessage) {
        RequestMessage requestMessage;
        try {
            ServerSessionManager serverSessionManager = ServerSessionManager.builder()
                    .machineSerialNumber(registerMessage.getMachineSerialNumber())
                    .ip(registerMessage.getIp()).port(registerMessage.getPort())
                    .channel(channelHandlerContext.channel())
                    .build();
            Registry.putServerSession(registerMessage.getMachineSerialNumber(), serverSessionManager);
            requestMessage = RequestMessage.newBuilder()
                    .setClientBehavior(RequestMessage.ClientBehavior.REGISTRY_ACK)
                    .setMessageDirect(RequestMessage.MessageDirect.SERVER)
                    .setContent(JSONUtil.beanToJson(Result.defaultSuccess(Code.REGISTER_SUCCESS)))
                    .setMachineSerialNumber(registerMessage.getMachineSerialNumber())
                    .setId(new SnowFlakeIdGenerator(registerMessage.getMachineSerialNumber(), Constant.MACHINE_SERIAL_NUMBER).nextId())
                    .build();

        } catch (Exception e) {
            requestMessage = RequestMessage.newBuilder()
                    .setMessageDirect(RequestMessage.MessageDirect.SERVER)
                    .setClientBehavior(RequestMessage.ClientBehavior.REGISTRY_ACK)
                    .setContent(JSONUtil.beanToJson(Result.defaultSuccess(Code.REGISTER_FAILURE)))
                    .setMachineSerialNumber(registerMessage.getMachineSerialNumber())
                    .setId(new SnowFlakeIdGenerator(registerMessage.getMachineSerialNumber(), Constant.MACHINE_SERIAL_NUMBER).nextId())
                    .build();
        }
        channelHandlerContext.channel().writeAndFlush(requestMessage);
    }

    public void dealRequestMessage(RequestMessage requestMessage) {
        long machineSerialNumber = (requestMessage).getMachineSerialNumber();
        ServerSessionManager serverSessionManager = Registry.getServerSession(machineSerialNumber);
        Channel serverChannel = serverSessionManager.getChannel();
        if (Objects.isNull(serverChannel)) {
            //一致性hash
        }
        serverChannel.writeAndFlush(requestMessage);
    }
}

package com.ghj.registry;


import com.ghj.common.base.Code;
import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.MessageProto;
import com.ghj.protocol.RegisterMessageProto.RegisterMessage;
import com.ghj.protocol.RequestMessageProto.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.ghj.protocol.MessageProto.Message.MessageBehavior.REGISTER;

/**
 * @author gehj
 * @date 2019/7/115:44
 */
public class BrokeHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        MessageProto.Message message = (MessageProto.Message)o;
        if (REGISTER == message.getMessageBehavior()) {
                dealRegisterMessage(channelHandlerContext, message);
        } else {
            dealRequestMessage(message);
        }

    }

    public void dealRegisterMessage(ChannelHandlerContext channelHandlerContext, MessageProto.Message message) {
        MessageProto.Message requestMessage;
        try {
            ServerSession serverSession = ServerSession.builder()
                    .machineSerialNumber(message.getMachineSerialNumber())
                    .ip(message.getIp()).port(message.getPort())
                    .channel(channelHandlerContext.channel())
                    .build();
            Registry.putServerSession(message.getMachineSerialNumber(), serverSession);
            //NettyAttrUtil.updateReaderTime(channelHandlerContext.channel(), System.currentTimeMillis() + Constant.PING_ADD_TIME);
            requestMessage = MessageProto.Message.newBuilder()
                    .setMessageBehavior(MessageProto.Message.MessageBehavior.REGISTRY_ACK)
                    .setMessageDirect(MessageProto.Message.MessageDirect.SERVER)
                    .setContent(JSONUtil.beanToJson(Result.defaultSuccess(Code.REGISTER_SUCCESS)))
                    .setMachineSerialNumber(message.getMachineSerialNumber())
                    .setId(new SnowFlakeIdGenerator(message.getMachineSerialNumber(), Constant.MACHINE_SERIAL_NUMBER).nextId())
                    .build();
        } catch (Exception e) {
            requestMessage = MessageProto.Message.newBuilder()
                    .setMessageDirect(MessageProto.Message.MessageDirect.SERVER)
                    .setMessageBehavior(MessageProto.Message.MessageBehavior.REGISTRY_ACK)
                    .setContent(JSONUtil.beanToJson(Result.defaultSuccess(Code.REGISTER_FAILURE)))
                    .setMachineSerialNumber(message.getMachineSerialNumber())
                    .setId(new SnowFlakeIdGenerator(message.getMachineSerialNumber(), Constant.MACHINE_SERIAL_NUMBER).nextId())
                    .build();
        }
        MessageDistributorCenter.getInstance().putMessage(requestMessage);
    }

    public void dealRequestMessage(MessageProto.Message message) {
        MessageDistributorCenter.getInstance().putMessage(message);
    }
}

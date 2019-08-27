package com.ghj.registry;


import com.ghj.common.base.Code;
import com.ghj.common.base.Constant;
import com.ghj.common.base.Result;
import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.MachineSerialNumber;
import com.ghj.common.util.NettyAttrUtil;
import com.ghj.common.util.SnowFlakeIdGenerator;
import com.ghj.protocol.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.ghj.protocol.MessageProto.Message.MessageBehavior.*;

/**
 * @author gehj
 * @date 2019/7/115:44
 */
public class BrokeHandler extends SimpleChannelInboundHandler {

    MessageProto.Message.ConnectType connectType;

    public BrokeHandler(MessageProto.Message.ConnectType connectType) {
        this.connectType = connectType;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.toString());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        System.out.println(o);
        MessageProto.Message message = (MessageProto.Message) o;
        if (PROXY_REGISTER == message.getMessageBehavior() || SERVER_REGISTER == message.getMessageBehavior()) {
            dealRegisterMessage(channelHandlerContext, message, message.getMessageBehavior());
        }
        if (PROXY_ROUTE == message.getMessageBehavior() || CLIENT_ROUTE == message.getMessageBehavior()) {
            dealRouteMessage(channelHandlerContext, message, message.getMessageBehavior());
        }

    }

    public void dealRegisterMessage(ChannelHandlerContext channelHandlerContext, MessageProto.Message message, MessageProto.Message.MessageBehavior messageBehavior) {
        MessageProto.Message ackMessage;
        try {
            NettyAttrUtil.updateReaderTime(channelHandlerContext.channel(), System.currentTimeMillis() + Constant.PING_ADD_TIME);
            Session session = Session.builder()
                    .ip(message.getIp()).port(message.getPort())
                    .channel(channelHandlerContext.channel())
                    .build();
            if (PROXY_REGISTER == message.getMessageBehavior()) {
                Registry.addProxySession(session);
            } else {
                Registry.addServerSession(session);
            }
            ackMessage = MessageProto.Message.newBuilder()
                    .setMessageBehavior(MessageProto.Message.MessageBehavior.ACK)
                    .setMessageDirect(MessageProto.Message.MessageDirect.SERVER)
                    .setContent(JSONUtil.beanToJson(Result.defaultSuccess(Code.REGISTER_SUCCESS)))
                    .setId(new SnowFlakeIdGenerator(MachineSerialNumber.get(), MachineSerialNumber.get()).nextId())
                    .build();
        } catch (Exception e) {
            ackMessage = MessageProto.Message.newBuilder()
                    .setMessageDirect(MessageProto.Message.MessageDirect.SERVER)
                    .setMessageBehavior(MessageProto.Message.MessageBehavior.ACK)
                    .setContent(JSONUtil.beanToJson(Result.defaultSuccess(Code.REGISTER_FAILURE)))
                    .setId(new SnowFlakeIdGenerator(MachineSerialNumber.get(), MachineSerialNumber.get()).nextId())
                    .build();
            e.printStackTrace();
        }
        channelHandlerContext.channel().writeAndFlush(ackMessage);
    }

    public void dealRouteMessage(ChannelHandlerContext channelHandlerContext, MessageProto.Message message, MessageProto.Message.MessageBehavior messageBehavior) {
        //策略
        //
        Session session;
        if (PROXY_ROUTE == message.getMessageBehavior()) {
            session = Registry.getBetterProxySession();
        } else {
            session = Registry.getBetterServerSession();
        }
        MessageProto.Message ackMessage = MessageProto.Message.newBuilder()
                .setMessageBehavior(ACK)
                .setIp(session.getIp())
                .setPort(session.getPort())
                .setMatchMessageId(message.getId())
                .build();
        channelHandlerContext.channel().writeAndFlush(ackMessage);
        channelHandlerContext.channel().close();
    }
}

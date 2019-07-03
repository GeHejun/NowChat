package com.ghj.registry;


import com.ghj.protocol.RegistrationMessageProto.*;
import com.ghj.protocol.RequestMessageProto.RequestMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gehj
 * @date 2019/7/115:44
 */
public class BrokeHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        if (o instanceof RegistrationMessage) {
            RegistrationMessage registerMessage = (RegistrationMessage) o;
            ServerSession serverSession = ServerSession.builder().machineSerialNumber(registerMessage.getMachineSerialNumber())
                    .ip(registerMessage.getIp()).port(registerMessage.getPort())
                    .channel(channelHandlerContext.channel()).build();
            Registry.putServerSession(registerMessage.getMachineSerialNumber(), serverSession);
        }
        if (o instanceof RequestMessage) {
            ServerSession serverSession = Registry.getServerSession(((RequestMessage) o).getMachineSerialNumber());
            Channel serverChannel = serverSession.getChannel();
//            if (Objects.isNull(serverChannel)) {
//
//            }
            serverChannel.writeAndFlush(o);
        }
    }
}

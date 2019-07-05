package com.ghj.android;

import com.ghj.common.base.Code;
import com.ghj.common.base.Constant;
import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RegisterMessageProto;
import com.ghj.protocol.RequestMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gehj
 * @date 2019/7/415:03
 */
public class ConnectHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)  {
        if (msg instanceof AckMessageProto.AckMessage) {
            AckMessageProto.AckMessage ackMessage = (AckMessageProto.AckMessage) msg;
            if (Code.LOGIN_SUCCESS.getCode() == ackMessage.getCode()) {

            } else {

            }
        }
        if (msg instanceof RequestMessageProto.RequestMessage) {
            RequestMessageProto.RequestMessage requestMessage = (RequestMessageProto.RequestMessage) msg;

        }
    }
}

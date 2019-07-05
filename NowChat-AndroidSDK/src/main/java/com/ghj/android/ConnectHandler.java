package com.ghj.android;

import com.ghj.common.base.Constant;
import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gehj
 * @date 2019/7/415:03
 */
public class ConnectHandler extends SimpleChannelInboundHandler {

    AckMessageListener ackMessageListener;

    RequestMessageListener requestMessageListener;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)  {
        if (msg instanceof AckMessageProto.AckMessage) {
            AckMessageProto.AckMessage ackMessage = (AckMessageProto.AckMessage) msg;
            switch (ackMessage.getCode()) {
                case Constant.LOGIN_SUCCESS_CODE:
                    break;
                    default:
            }

        }
        if (msg instanceof RequestMessageProto.RequestMessage) {
            RequestMessageProto.RequestMessage requestMessage = (RequestMessageProto.RequestMessage) msg;

        }
    }
}

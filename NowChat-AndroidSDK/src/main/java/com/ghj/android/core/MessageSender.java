package com.ghj.android.core;

import com.ghj.protocol.RequestMessageProto;
import io.netty.channel.Channel;

/**
 * @author gehj
 * @date 2019/7/514:35
 */
public class MessageSender implements Runnable {

    RequestMessageProto.RequestMessage requestMessage;
    Channel channel;


    public MessageSender(Channel channel, RequestMessageProto.RequestMessage requestMessage){
        this.requestMessage = requestMessage;
        this.channel = channel;
    }
    @Override
    public void run() {

    }
}

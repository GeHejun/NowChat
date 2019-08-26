package com.ghj.android.core.message;

import com.ghj.protocol.MessageProto;
import io.netty.channel.Channel;

/**
 * @author gehj
 * @date 2019/7/514:35
 */
public class MessageSender implements Runnable {

    MessageProto.Message message;
    Channel channel;


    public MessageSender(Channel channel, MessageProto.Message requestMessage){
        this.message = requestMessage;
        this.channel = channel;
    }
    @Override
    public void run() {

    }
}

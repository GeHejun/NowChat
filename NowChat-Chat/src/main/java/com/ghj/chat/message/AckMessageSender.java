package com.ghj.chat.message;

import com.ghj.chat.Session;
import com.ghj.chat.protocol.AckMessageProto;
import com.ghj.common.dto.AbstractMessage;

public class AckMessageSender implements Runnable{

    public AckMessageProto.AckMessage message;



    Session session;

    AbstractMessage abstractMessage;

    public AckMessageSender(AckMessageProto.AckMessage message) {
        this.message = message;
    }

    @Override
    public void run() {

    }
}

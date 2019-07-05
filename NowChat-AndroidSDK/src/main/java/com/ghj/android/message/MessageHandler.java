package com.ghj.android.message;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;

/**
 * @author gehj
 * @date 2019/7/511:11
 */
public class MessageHandler implements AckMessageListener,RequestMessageListener{



    @Override
    public void dealAckMessage(AckMessageProto.AckMessage ackMessage) {

    }

    @Override
    public void dealRequestMessage(RequestMessageProto.RequestMessage requestMessage) {

    }
}

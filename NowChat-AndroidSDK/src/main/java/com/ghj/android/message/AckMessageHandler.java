package com.ghj.android.message;

import com.ghj.protocol.AckMessageProto;

/**
 * @author gehj
 * @date 2019/7/510:14
 */
public interface AckMessageHandler extends MessageHandler {

    void dealAckMessage(AckMessageProto.AckMessage ackMessage);
}

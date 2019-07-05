package com.ghj.android.ack;

import com.ghj.protocol.AckMessageProto;

/**
 * @author gehj
 * @date 2019/7/510:14
 */
public interface AckMessageHandler {

    void dealAckMessage(AckMessageProto.AckMessage ackMessage);
}

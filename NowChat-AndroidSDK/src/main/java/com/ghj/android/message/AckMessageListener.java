package com.ghj.android.message;

import com.ghj.protocol.AckMessageProto;

/**
 * @author gehj
 * @date 2019/7/59:48
 */
public interface AckMessageListener {
    /**
     * 处理ack消息
     * @param ackMessage
     */
    void dealAckMessage(AckMessageProto.AckMessage ackMessage);
}

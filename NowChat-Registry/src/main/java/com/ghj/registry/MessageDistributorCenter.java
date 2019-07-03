package com.ghj.registry;

import com.ghj.protocol.RequestMessageProto;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author gehj
 * @date 2019/7/314:13
 */
public class MessageDistributorCenter {

    private MessageDistributorCenter() {}

    private static Object MESSAGE_DISTRIBUTOR_CENTER_LOCK = new Object();

    private static volatile MessageDistributorCenter messageDistributorCenter;

    public static MessageDistributorCenter getInstance() {

        if (messageDistributorCenter == null) {
            synchronized (MESSAGE_DISTRIBUTOR_CENTER_LOCK) {
                if (messageDistributorCenter == null) {
                    messageDistributorCenter = new MessageDistributorCenter();
                    messageDistributorCenter.takeMessage();
                }
            }
        }
        return messageDistributorCenter;
    }

    public ConcurrentLinkedQueue requestMessageQueue = new ConcurrentLinkedQueue();

   public void takeMessage() {
        for (;;) {
            requestMessageQueue.poll();
        }
   }

    public void putMessage(RequestMessageProto.RequestMessage requestMessage) {
        requestMessageQueue.add(requestMessage);
    }
}

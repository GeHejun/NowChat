package com.ghj.registry;

import com.ghj.common.util.ThreadPoolManager;
import com.ghj.protocol.MessageProto;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author gehj
 * @date 2019/7/314:13
 */
public class MessageDistributorCenter {

    private MessageDistributorCenter() {}

    private final static Object MESSAGE_DISTRIBUTOR_CENTER_LOCK = new Object();

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
            if (requestMessageQueue.isEmpty()) {
                continue;
            }
            ThreadPoolManager.getsInstance().execute(new RequestMessageDistributor((MessageProto.Message) requestMessageQueue.poll()));
        }
   }

    public void putMessage(MessageProto.Message requestMessage) {
        requestMessageQueue.add(requestMessage);
    }
}

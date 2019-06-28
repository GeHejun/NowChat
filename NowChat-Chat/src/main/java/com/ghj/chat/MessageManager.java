package com.ghj.chat;

import com.ghj.chat.protocol.MessageProto;
import com.ghj.common.util.ThreadPoolManager;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author GeHejun
 * @date 2019/6/24 13:13
 */
public class MessageManager {

    private MessageManager() {}

    private static Object MESSAGE_MANAGER_LOCK = new Object();

    private static volatile MessageManager messageManager;

    public static MessageManager getInstance() {
        if (messageManager == null) {
            synchronized (MESSAGE_MANAGER_LOCK) {
                if (messageManager == null) {
                    messageManager = new MessageManager();
                    messageManager.takeMessage();
                }
            }
        }
        return messageManager;
    }

    private ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

    public void putMessage(MessageProto.message message) {
        concurrentLinkedQueue.add(message);
    }

    public void takeMessage() {
        for (;;) {
            ThreadPoolManager.getsInstance().execute(new MessageSender((MessageProto.message) concurrentLinkedQueue.poll()));
        }

    }

}

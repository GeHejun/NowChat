package com.ghj.chat;

import com.ghj.chat.protocol.AckMessageProto;
import com.ghj.chat.protocol.RequestMessageProto;
import com.ghj.common.exception.MessageException;
import com.ghj.common.util.ThreadPoolManager;

import java.util.Objects;
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

    private ConcurrentLinkedQueue waitSendMessageQueue = new ConcurrentLinkedQueue();

    private ConcurrentLinkedQueue failureRequestMessageQueue = new ConcurrentLinkedQueue();

    private ConcurrentLinkedQueue ackMessageQueue = new ConcurrentLinkedQueue();

    private ConcurrentLinkedQueue failureAckMessageQueue = new ConcurrentLinkedQueue();

    public void putMessage(RequestMessageProto.RequestMessage message) {
        if (Objects.isNull(message)) {
            throw new MessageException();
        }
        waitSendMessageQueue.add(message);
    }

    public void takeMessage() {
        for (;;) {
            ThreadPoolManager.getsInstance().execute(new RequestMessageSender((RequestMessageProto.RequestMessage) waitSendMessageQueue.poll()));
        }
    }



    public void failureRequestMessage(RequestMessageProto.RequestMessage message) {

        failureRequestMessageQueue.add(message);

        ThreadPoolManager.getsInstance().execute(() -> {
            waitSendMessageQueue.add(failureRequestMessageQueue.poll());
        });
    }

    public void failureAckMessage(RequestMessageProto.RequestMessage message) {

        failureAckMessageQueue.add(message);

        ThreadPoolManager.getsInstance().execute(() -> {
            ackMessageQueue.add(failureAckMessageQueue.poll());
        });
    }

    public void ackMessageQueue(AckMessageProto.AckMessage message) {

        ackMessageQueue.add(message);
        for (;;) {
            ThreadPoolManager.getsInstance().execute(new AckMessageSender((AckMessageProto.AckMessage)ackMessageQueue.poll()));
        }
    }



}

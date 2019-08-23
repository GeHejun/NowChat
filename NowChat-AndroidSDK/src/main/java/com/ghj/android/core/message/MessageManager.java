package com.ghj.android.core.message;


import com.ghj.android.core.Constant;
import com.ghj.common.util.ThreadPoolManager;
import com.ghj.protocol.MessageProto;
import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @author gehj
 * @date 2019/7/415:12
 */
public class MessageManager {

    private MessageManager() {}

    public static MessageManager messageManager;

    public static CountDownLatch countDownLatch = new CountDownLatch(Constant.WAIT_LOGIN_MESSAGE_COUNT);

    public static MessageManager getInstance() {
        if (messageManager == null) {
            synchronized (MessageManager.class) {
                messageManager = new MessageManager();
            }
        }
        return messageManager;
    }

    private Channel channel;

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ConcurrentLinkedQueue messageQueue = new ConcurrentLinkedQueue();

    public void takeMessage() {
        for (;;) {
            MessageProto.Message  requestMessage = (MessageProto.Message) messageQueue.poll();
            ThreadPoolManager.getsInstance().execute(new MessageSender(channel, requestMessage));
        }

    }
    public void sendLoginMessage(MessageProto.Message requestMessage) {
        ThreadPoolManager.getsInstance().execute(new MessageSender(channel, requestMessage));
    }
}

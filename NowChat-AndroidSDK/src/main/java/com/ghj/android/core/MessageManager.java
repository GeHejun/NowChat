package com.ghj.android.core;


import io.netty.channel.Channel;

/**
 * @author gehj
 * @date 2019/7/415:12
 */
public class MessageManager {

    private MessageManager() {}

    public static MessageManager messageManager;

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
}

package com.ghj.chat;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectManager {

    private static ConcurrentHashMap CHANNEL_MAP = new ConcurrentHashMap(16);

    public static void putChannel(String requestId, Channel channel) {
        CHANNEL_MAP.put(requestId, channel);
    }

    public static Channel getChannel(String requestId) {
        return (Channel) CHANNEL_MAP.get(requestId);
    }
}

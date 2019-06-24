package com.ghj.chat;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static ConcurrentHashMap SESSION_MAP = new ConcurrentHashMap(16);

    public static void putSession(String requestId, Channel channel) {
        SESSION_MAP.put(requestId, channel);
    }

    public static Channel getSession(String requestId) {
        return (Channel) SESSION_MAP.get(requestId);
    }
}

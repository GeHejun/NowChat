package com.ghj.chat;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static ConcurrentHashMap SESSION_MAP = new ConcurrentHashMap(16);

    public static void putSession(Integer userId, Session session) {
        SESSION_MAP.put(userId, session);
    }

    public static Session getSession(Integer userId) {
        return (Session) SESSION_MAP.get(userId);
    }
}

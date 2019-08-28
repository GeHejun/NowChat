package com.ghj.registry;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gehj
 * @date 2019/7/112:49
 */
public class Registry {

    private static List<Session> PROXY_WEBSOCKET_SESSION_LIST = new ArrayList<>();

    private static List<Session> PROXY_NETTY_SESSION_LIST = new ArrayList<>();

    private static List<Session> SERVER_SESSION_LIST = new ArrayList<>();

    public static void addNettyProxySession(Session session) {
        if (session != null) {
            PROXY_NETTY_SESSION_LIST.add(session);
        }

    }

    public static Session getBetterNettyProxySession() {
        return PROXY_NETTY_SESSION_LIST.get(0);
    }

    public static void addWebSocketProxySession(Session session) {
        if (session != null) {
            PROXY_WEBSOCKET_SESSION_LIST.add(session);
        }

    }

    public static Session getBetterWebSocketProxySession() {
        return PROXY_WEBSOCKET_SESSION_LIST.get(0);
    }


    public static void addServerSession(Session session) {
        if (session != null) {
            SERVER_SESSION_LIST.add(session);
        }

    }
    public static Session getBetterServerSession() {
        return SERVER_SESSION_LIST.get(0);
    }

    public static void remove(Channel channel) {
        for (Session session:PROXY_NETTY_SESSION_LIST) {
            if (session.getChannel().equals(channel)) {
                PROXY_NETTY_SESSION_LIST.remove(session);
            }
        }
        for (Session session:PROXY_WEBSOCKET_SESSION_LIST) {
            if (session.getChannel().equals(channel)) {
                PROXY_WEBSOCKET_SESSION_LIST.remove(session);
            }
        }
        for (Session session:SERVER_SESSION_LIST) {
            if (session.getChannel().equals(channel)) {
                SERVER_SESSION_LIST.remove(session);
            }
        }
    }



//    public static void watchServerSessionStatus() {
//        ThreadPoolManager.getsInstance().execute(() -> {
//            for (; ; ) {
//                SERVER_SESSION_MAP.forEach((k, v) -> {
//                    if (NettyAttrUtil.getReaderTime(v.getChannel()) < System.currentTimeMillis()) {
//
//                        Thread.yield();
//                    }
//                });
//            }
//        });
//    }
}

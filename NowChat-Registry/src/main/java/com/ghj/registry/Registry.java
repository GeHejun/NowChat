package com.ghj.registry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gehj
 * @date 2019/7/112:49
 */
public class Registry {

    private static List<Session> PROXY_SESSION_LIST = new ArrayList<>();

    private static List<Session> SERVER_SESSION_LIST = new ArrayList<>();

    public static void addProxySession(Session session) {
        PROXY_SESSION_LIST.add(session);
    }

    public static Session getBetterProxySession() {
        return PROXY_SESSION_LIST.get(0);
    }

    public static void addServerSession(Session session) {
        SERVER_SESSION_LIST.add(session);
    }

    public static Session getBetterServerSession() {
        return SERVER_SESSION_LIST.get(0);
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

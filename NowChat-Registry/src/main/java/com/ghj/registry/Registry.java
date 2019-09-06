package com.ghj.registry;

import java.util.ArrayList;
import java.util.Comparator;
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
        if (PROXY_WEBSOCKET_SESSION_LIST.size() > 1) {
            PROXY_WEBSOCKET_SESSION_LIST.stream().sorted(Comparator.comparing(Session::getVersion));
        }
        return PROXY_WEBSOCKET_SESSION_LIST.get(0);
    }


    public static void addServerSession(Session session) {
        if (session != null) {
            SERVER_SESSION_LIST.add(session);
        }

    }
    public static Session getBetterServerSession() {
//        Map<Integer, Session> connects = new HashMap<>(16);
//        for (Session session:SERVER_SESSION_LIST) {
//            int num = Integer.parseInt(RedisPoolUtil.get(Constant.ON_LINE_USER_COUNT + "_" + session.getIp() + "_" + session.getPort()));
//            if (num > Constant.MAX_CONNECT_NUM) {
//                continue;
//            }
//            connects.put(num, session);
//        }
//        if (connects.isEmpty()) {
//            //
//        }
//        return connects.get(connects.keySet().stream().sorted(Comparator.reverseOrder()).findFirst());
        return SERVER_SESSION_LIST.get(0);
    }




//    public static void watchServerSessionStatus() {
//        ThreadPoolManager.getsInstance().execute(() -> {
//            for (; ;) {
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

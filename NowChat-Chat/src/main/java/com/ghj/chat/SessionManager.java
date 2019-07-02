package com.ghj.chat;

import com.ghj.common.util.NettyAttrUtil;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class SessionManager {

    private static ConcurrentHashMap SESSION_MAP = new ConcurrentHashMap(16);

    public static void putSession(Integer id, Session session) {
        SESSION_MAP.put(id, session);
    }

    public static Session getSession(Integer id) {
        return (Session) SESSION_MAP.get(id);
    }

    public static Session removeSession(Integer id) {
        return (Session) SESSION_MAP.remove(id);
    }


    public static void watchSessionStatus() {
        new Thread(()->{
            for (;;) {
                SESSION_MAP.forEach((k,v)->{
                    if (NettyAttrUtil.getReaderTime(((Session)v).channel) < System.currentTimeMillis()) {
                        removeSession((Integer) k);
                        Thread.yield();
                    }
                });
            }
        }).start();
    }

}

package com.ghj.chat;

import java.util.concurrent.ConcurrentHashMap;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class SessionManager {

    private static ConcurrentHashMap SESSION_MAP = new ConcurrentHashMap(16);

    public static void putSession(Integer userId, Session session) {
        SESSION_MAP.put(userId, session);
    }

    public static Session getSession(Integer userId) {
        return (Session) SESSION_MAP.get(userId);
    }
}

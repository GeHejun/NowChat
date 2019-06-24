package com.ghj.chat;

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
}

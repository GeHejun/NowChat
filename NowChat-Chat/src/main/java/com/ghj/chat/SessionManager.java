package com.ghj.chat;

import com.ghj.common.util.MachineSerialNumber;
import com.ghj.common.util.NettyAttrUtil;
import com.ghj.common.util.RedisPoolUtil;
import com.ghj.common.util.ThreadPoolManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class SessionManager {

    private static ConcurrentHashMap SESSION_MAP = new ConcurrentHashMap(16);

    public static void putSession(Integer id, Session session) {
        SESSION_MAP.put(id, session);
        RedisPoolUtil.hset(session.getChannel().localAddress().toString(), id.toString(), session);
    }

    public static Session getSession(Integer id) {
        return (Session) SESSION_MAP.get(id);
    }

    public static Session removeSession(Integer id) {
        return (Session) SESSION_MAP.remove(id);
    }


    public static void watchSessionStatus() {
        ThreadPoolManager.getsInstance().execute(()->{
            for (;;) {
                SESSION_MAP.forEach((k,v)->{
                    if (v != null && ((Session)v).channel != null && NettyAttrUtil.getReaderTime(((Session)v).channel) != null) {
                        if (NettyAttrUtil.getReaderTime(((Session)v).channel) < System.currentTimeMillis()) {
                            removeSession((Integer) k);
                            Thread.yield();
                        }
                    }

                });
            }
        });
    }

}

package com.ghj.registry;

import com.ghj.common.util.NettyAttrUtil;
import com.ghj.common.util.ThreadPoolManager;
import com.ghj.protocol.MessageProto;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gehj
 * @date 2019/7/112:49
 */
public class Registry {

    public static ConcurrentHashMap<String, ServerSession> SERVER_SESSION_MAP = new ConcurrentHashMap(32);

    public static void putServerSession(String ip, MessageProto.Message.ConnectType connectType, ServerSession serverSession) {
        SERVER_SESSION_MAP.put(ip + "_" + connectType, serverSession);
    }

    public static ServerSession getServerSession(String ip, MessageProto.Message.ConnectType connectType) {
        return SERVER_SESSION_MAP.get(ip + "_" + connectType);
    }

    public static void watchServerSessionStatus() {
        ThreadPoolManager.getsInstance().execute(() -> {
            for (; ; ) {
                SERVER_SESSION_MAP.forEach((k, v) -> {
                    if (NettyAttrUtil.getReaderTime(v.getChannel()) < System.currentTimeMillis()) {

                        Thread.yield();
                    }
                });
            }
        });
    }
}

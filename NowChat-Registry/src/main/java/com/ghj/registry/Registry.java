package com.ghj.registry;

import com.ghj.common.util.NettyAttrUtil;
import com.ghj.common.util.ThreadPoolManager;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gehj
 * @date 2019/7/112:49
 */
public class Registry {

    public static ConcurrentHashMap<Long, ServerSession> SERVER_SESSION_MAP = new ConcurrentHashMap(32);

    public static void putServerSession(Long machineSerialNumber, ServerSession serverSession) {
        SERVER_SESSION_MAP.put(machineSerialNumber, serverSession);
    }

    public static ServerSession getServerSession(Long machineSerialNumber) {
        return SERVER_SESSION_MAP.get(machineSerialNumber);
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

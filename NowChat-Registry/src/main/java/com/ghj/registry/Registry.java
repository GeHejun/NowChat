package com.ghj.registry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gehj
 * @date 2019/7/112:49
 */
public class Registry {

    public static ConcurrentHashMap<Long, ServerSessionManager> SERVER_SESSION_MAP = new ConcurrentHashMap(32);

    public static void putServerSession(Long machineSerialNumber, ServerSessionManager serverSessionManager) {
        SERVER_SESSION_MAP.put(machineSerialNumber, serverSessionManager);
    }

    public static ServerSessionManager getServerSession(Long machineSerialNumber) {
        return SERVER_SESSION_MAP.get(machineSerialNumber);
    }
}

package com.ghj.proxy;

import java.nio.channels.Channel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 13:36
 */
public class SessionManager {

    public static ConcurrentHashMap<Channel, Channel> SERVER_SESSION_MAP = new ConcurrentHashMap();

    public static ConcurrentHashMap<Channel, Channel> CLIENT_SESSION_MAP = new ConcurrentHashMap();

    public static void bindServer(Channel client, Channel server) {
        SERVER_SESSION_MAP.put(client, server);
    }

    public static void bindClient(Channel server, Channel client) {
        SERVER_SESSION_MAP.put(server, client);
    }

    public static Channel getServer(Channel client) {
        return SERVER_SESSION_MAP.get(client);
    }

    public static Channel getClient(Channel server) {
        return CLIENT_SESSION_MAP.get(server);
    }
}

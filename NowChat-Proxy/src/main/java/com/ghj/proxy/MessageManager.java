package com.ghj.proxy;

import com.ghj.protocol.MessageProto;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 16:55
 */
public class MessageManager {
    public static ConcurrentHashMap<Long, MessageProto.Message> ROUTE_MESSAGE_MAP = new ConcurrentHashMap(32);

    public static void putMessageRoute(Long id, MessageProto.Message message) {
        ROUTE_MESSAGE_MAP.put(id, message);
    }

    public MessageProto.Message getMessageRoute(Long id) {
        return ROUTE_MESSAGE_MAP.get(id);
    }
}

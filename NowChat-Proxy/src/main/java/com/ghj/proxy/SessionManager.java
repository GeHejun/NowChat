package com.ghj.proxy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 13:36
 */
public class SessionManager {

    public ConcurrentHashMap SERVER_SESSION_MAP = new ConcurrentHashMap();

    public ConcurrentHashMap CLIENT_SESSION_MAP = new ConcurrentHashMap();
}

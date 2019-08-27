package com.ghj.proxy;

import com.ghj.common.util.ThreadPoolManager;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 11:45
 */
public class ProxyStarter {
    public static void main(String[] args) {
        NettyConnector nettyConnector = new NettyConnector();
        WebSocketConnector webSocketConnector = new WebSocketConnector();
        try {
            ThreadPoolManager.getsInstance().execute(()->
                    nettyConnector.start(6999)
            );
            ThreadPoolManager.getsInstance().execute(()->
                    webSocketConnector.start(6998)
            );
        } catch (Exception e) {
            e.printStackTrace();
            nettyConnector.stop();
            webSocketConnector.stop();
        }
    }
}

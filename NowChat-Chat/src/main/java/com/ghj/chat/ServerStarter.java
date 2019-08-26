package com.ghj.chat;

import com.ghj.common.util.ThreadPoolManager;

/**
 * @author gehj
 * @date 2019/6/2414:26
 */
public class ServerStarter {

    public static void main(String[] args) {
        NettyConnector nettyConnector = new NettyConnector();
        WebSocketConnector webSocketConnector = new WebSocketConnector();
        try {
            ThreadPoolManager.getsInstance().execute(() -> webSocketConnector.start(8991));
            ThreadPoolManager.getsInstance().execute(()-> nettyConnector.start(8990));
            SessionManager.watchSessionStatus();
        } catch (Exception e) {
            e.printStackTrace();
            nettyConnector.stop();
            webSocketConnector.stop();
        }
    }

}

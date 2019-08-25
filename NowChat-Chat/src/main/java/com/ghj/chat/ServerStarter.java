package com.ghj.chat;

import com.ghj.common.util.ThreadPoolManager;

/**
 * @author gehj
 * @date 2019/6/2414:26
 */
public class ServerStarter {
//    public static void main(String[] args) {
//        Connector connector = new Connector();
//        try {
//            connector.start(8990);
//            SessionManager.watchSessionStatus();
//        } catch (Exception e) {
//            e.printStackTrace();
//            connector.stop();
//        }
//
//    }

    public static void main(String[] args) {
        Connector connector = new Connector();
        WebSocketConnector webSocketConnector = new WebSocketConnector();
        try {
            ThreadPoolManager.getsInstance().execute(()-> connector.start(8990));
            ThreadPoolManager.getsInstance().execute(() -> webSocketConnector.start(8991));
            SessionManager.watchSessionStatus();
        } catch (Exception e) {
            e.printStackTrace();
            connector.stop();
            webSocketConnector.stop();
        }
    }

}

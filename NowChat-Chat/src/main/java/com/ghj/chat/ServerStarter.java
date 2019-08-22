package com.ghj.chat;

/**
 * @author gehj
 * @date 2019/6/2414:26
 */
public class ServerStarter {
    public static void main(String[] args) {
        Connector connector = new Connector();
        try {
            connector.start(8990);
            SessionManager.watchSessionStatus();
        } catch (Exception e) {
            e.printStackTrace();
            connector.stop();
        }

    }

}

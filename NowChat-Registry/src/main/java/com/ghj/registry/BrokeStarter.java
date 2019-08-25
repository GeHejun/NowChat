package com.ghj.registry;

import com.ghj.common.util.ThreadPoolManager;

/**
 * @author gehj
 * @date 2019/7/115:46
 */
public class BrokeStarter {
    public static void main(String[] args) {
        WebSocketBroker webSocketBroker = new WebSocketBroker();
        TcpBroker tcpBroker = new TcpBroker();
        try {
            ThreadPoolManager.getsInstance().execute(()->{
                webSocketBroker.start(8798);
            });
            ThreadPoolManager.getsInstance().execute(()->{
                tcpBroker.start(8799);
            });

        } catch (Exception e) {
            e.printStackTrace();
            webSocketBroker.stop();
        }

    }
}

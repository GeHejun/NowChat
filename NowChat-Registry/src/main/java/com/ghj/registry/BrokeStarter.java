package com.ghj.registry;

import com.ghj.common.util.ThreadPoolManager;

/**
 * @author gehj
 * @date 2019/7/115:46
 */
public class BrokeStarter {
    public static void main(String[] args) {
        WebSocketBroker webSocketBroker = new WebSocketBroker();
        NettyBroker nettyBroker = new NettyBroker();
        try {
            ThreadPoolManager.getsInstance().execute(()->
                nettyBroker.start(9999)
            );
            ThreadPoolManager.getsInstance().execute(()->
                webSocketBroker.start(9998)
            );
        } catch (Exception e) {
            e.printStackTrace();
            webSocketBroker.stop();
            nettyBroker.stop();
        }

    }
}

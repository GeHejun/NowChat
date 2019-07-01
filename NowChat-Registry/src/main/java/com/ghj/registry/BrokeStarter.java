package com.ghj.registry;

/**
 * @author gehj
 * @date 2019/7/115:46
 */
public class BrokeStarter {
    public static void main(String[] args) {
        Broker broker = new Broker();
        try {
            broker.start(8798);
        } catch (Exception e) {
            e.printStackTrace();
            broker.stop();
        }

    }
}

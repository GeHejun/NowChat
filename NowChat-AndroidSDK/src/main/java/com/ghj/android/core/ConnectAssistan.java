package com.ghj.android.core;


import com.ghj.android.core.message.MessageManager;
import com.ghj.android.core.observer.Observer;
import com.ghj.android.core.observer.Subject;

import java.util.List;

/**
 * @author gehj
 * @date 2019/7/109:55
 */
public class ConnectAssistan {

    Subject subject =  new Subject();

    public void attach(List<Observer> messageObservers) {
        messageObservers.forEach(messageObserver -> subject.attachObserver(messageObserver));
    }


    public void work(String host, int port) {
        Connnector connnector = new Connnector();
        MessageManager messageManager = MessageManager.getInstance();
        try {
            connnector.connect(host, port, messageManager,subject);
        } catch (Exception e) {
            e.printStackTrace();
            connnector.stop();
        }

    }
}

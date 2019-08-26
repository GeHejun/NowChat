package com.ghj.android.core.observer;



import com.ghj.protocol.MessageProto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GeHejun
 */
public class Subject {


    List<Observer> observerList = new ArrayList<>();

    public void attachObserver(Observer observer) {
        observerList.add(observer);
    }

    public void detachObserver(Observer observer) {
        observerList.remove(observer);
    }


    public void notifyAllListener(MessageProto.Message message) {
        for (Observer observer:observerList) {
            observer.messageListener(message);
        }
    }
}

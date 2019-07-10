package com.ghj.android.core.observer;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    List<Observer> ackObserverList = new ArrayList<>();

    List<Observer> requestObserverList = new ArrayList<>();

    public void attachAckObserver(Observer observer) {
        ackObserverList.add(observer);
    }

    public void detachAckObserver(Observer observer) {
        ackObserverList.remove(observer);
    }

    public void attachRequestObserver(Observer observer) {
        requestObserverList.add(observer);
    }

    public void detachRequestObserver(Observer observer) {
        requestObserverList.remove(observer);
    }

    public void ackNotifyAllListener(AckMessageProto.AckMessage ackMessage) {
        for (Observer observer:ackObserverList) {
            observer.ackMessageListener(ackMessage);
        }
    }

    public void requestNotifyAllListener(RequestMessageProto.RequestMessage requestMessage) {
        for (Observer observer:requestObserverList) {
            observer.requestMessageListener(requestMessage);
        }
    }
}

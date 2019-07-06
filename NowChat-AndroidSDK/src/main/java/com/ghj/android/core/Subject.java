package com.ghj.android.core;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    List<Observer> loginObserverList = new ArrayList<>();

    List<Observer> requestObserverList = new ArrayList<>();

    public void attachLoginObserver(Observer observer) {
        loginObserverList.add(observer);
    }

    public void detachLoginObserver(Observer observer) {
        loginObserverList.remove(observer);
    }

    public void attachRequestObserver(Observer observer) {
        requestObserverList.add(observer);
    }

    public void detachRequestObserver(Observer observer) {
        requestObserverList.remove(observer);
    }

    public void loginNotifyAllListener(AckMessageProto.AckMessage ackMessage) {
        for (Observer observer:loginObserverList) {
            observer.loginMessageListener(ackMessage);
        }
    }

    public void requestNotifyAllListener(RequestMessageProto.RequestMessage requestMessage) {
        for (Observer observer:requestObserverList) {
            observer.requestMessageListener(requestMessage);
        }
    }
}

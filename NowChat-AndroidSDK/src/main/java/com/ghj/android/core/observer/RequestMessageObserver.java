package com.ghj.android.core.observer;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;

/**
 * @author gehj
 * @date 2019/7/1010:33
 */
public class RequestMessageObserver implements Observer {

    RequestMessageCallBack requestMessageCallBack;

    public RequestMessageObserver(RequestMessageCallBack requestMessageCallBack) {
        this.requestMessageCallBack = requestMessageCallBack;
    }


    @Override
    public void ackMessageListener(AckMessageProto.AckMessage ackMessage) {
        return;
    }

    @Override
    public void requestMessageListener(RequestMessageProto.RequestMessage requestMessage) {
        requestMessageCallBack.dealRequestMessage(requestMessage);
    }
}

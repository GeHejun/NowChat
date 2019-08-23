package com.ghj.android.core.observer;

import com.ghj.protocol.MessageProto;

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
    public void ackMessageListener(MessageProto.Message ackMessage) {
        return;
    }

    @Override
    public void requestMessageListener(MessageProto.Message requestMessage) {
        requestMessageCallBack.dealRequestMessage(requestMessage);
    }
}

package com.ghj.android.core.observer;

import com.ghj.common.base.Constant;
import com.ghj.protocol.MessageProto;

/**
 * @author gehj
 * @date 2019/7/1010:33
 */
public class MessageObserver implements Observer {

    MessageCallBack messageCallBack;

    public MessageObserver(MessageCallBack messageCallBack) {
        this.messageCallBack = messageCallBack;
    }

    @Override
    public void ackMessageListener(MessageProto.Message ackMessage) {
        if (Constant.MESSAGE_SEND_SUCCESS_CODE ==  ackMessage.getCode()) {
            messageCallBack.success(ackMessage);
        }
        if (Constant.MESSAGE_SEND_FAILURE_CODE == ackMessage.getCode()) {
            messageCallBack.failure(ackMessage);
        }
    }

    @Override
    public void requestMessageListener(MessageProto.Message requestMessage) {
        return;
    }
}

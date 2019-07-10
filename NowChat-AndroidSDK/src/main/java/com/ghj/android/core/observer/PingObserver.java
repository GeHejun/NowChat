package com.ghj.android.core.observer;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;
import com.ghj.common.base.Constant;

/**
 * @author gehj
 * @date 2019/7/1010:29
 */
public class PingObserver implements Observer {

    private PingCallBack pingCallBack;

    public PingObserver(PingCallBack pingCallBack) {
        this.pingCallBack = pingCallBack;
    }

    @Override
    public void ackMessageListener(AckMessageProto.AckMessage ackMessage) {
        if (Constant.PING_SUCCESS_CODE == ackMessage.getCode()) {
            pingCallBack.success(ackMessage);
        }
        if (Constant.PING_FAILURE_CODE == ackMessage.getCode()) {
            pingCallBack.failure(ackMessage);
        }
    }

    @Override
    public void requestMessageListener(RequestMessageProto.RequestMessage requestMessage) {
        return;
    }
}

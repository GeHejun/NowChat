package com.ghj.android.core;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;

/**
 * @author gehj
 * @date 2019/7/1010:29
 */
public class PingObserver implements Observer {

    @Override
    public void ackMessageListener(AckMessageProto.AckMessage ackMessage) {

    }

    @Override
    public void requestMessageListener(RequestMessageProto.RequestMessage requestMessage) {
        return;
    }
}

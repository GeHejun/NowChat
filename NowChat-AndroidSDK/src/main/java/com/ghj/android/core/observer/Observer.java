package com.ghj.android.core.observer;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;

public interface Observer {

    void ackMessageListener(AckMessageProto.AckMessage ackMessage);

    void requestMessageListener(RequestMessageProto.RequestMessage requestMessage);
}

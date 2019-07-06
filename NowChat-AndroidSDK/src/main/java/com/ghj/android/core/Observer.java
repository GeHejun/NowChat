package com.ghj.android.core;

import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;

public interface Observer {

    void loginMessageListener(AckMessageProto.AckMessage ackMessage);

    void requestMessageListener(RequestMessageProto.RequestMessage requestMessage);
}

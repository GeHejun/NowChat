package com.ghj.android.core.observer;

import com.ghj.protocol.MessageProto;

public interface Observer {

    void ackMessageListener(MessageProto.Message ackMessage);

    void requestMessageListener(MessageProto.Message requestMessage);
}

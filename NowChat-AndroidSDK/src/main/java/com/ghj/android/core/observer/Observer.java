package com.ghj.android.core.observer;

import com.ghj.protocol.MessageProto;

public interface Observer {


    void messageListener(MessageProto.Message requestMessage);
}

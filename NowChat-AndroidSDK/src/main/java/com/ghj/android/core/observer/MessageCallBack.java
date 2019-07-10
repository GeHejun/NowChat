package com.ghj.android.core.observer;

import com.ghj.protocol.AckMessageProto;

/**
 * @author gehj
 * @date 2019/7/1011:38
 */
public interface MessageCallBack {

    void success(AckMessageProto.AckMessage ackMessage);

    void failure(AckMessageProto.AckMessage ackMessage);
}

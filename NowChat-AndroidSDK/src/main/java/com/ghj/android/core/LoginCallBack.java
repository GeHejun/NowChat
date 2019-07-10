package com.ghj.android.core;

import com.ghj.protocol.AckMessageProto;

/**
 * @author gehj
 * @date 2019/7/1011:36
 */
public interface LoginCallBack {

    void success(AckMessageProto.AckMessage ackMessage);
    void failure(AckMessageProto.AckMessage ackMessage);
}

package com.ghj.android.core.observer;

import com.ghj.protocol.MessageProto;

/**
 * @author gehj
 * @date 2019/7/1011:36
 */
public interface LoginCallBack {

    void success(MessageProto.Message ackMessage);

    void failure(MessageProto.Message ackMessage);
}

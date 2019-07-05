package com.ghj.android.request;

import com.ghj.protocol.RequestMessageProto;

/**
 * @author gehj
 * @date 2019/7/59:49
 */
public interface RequestMessageListener {
    /**
     * 处理别人发送的消息
     * @param requestMessage
     */
    void dealRequestMessage(RequestMessageProto.RequestMessage requestMessage);
}

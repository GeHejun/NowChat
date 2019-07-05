package com.ghj.android.message;

import com.ghj.protocol.RequestMessageProto;

/**
 * @author gehj
 * @date 2019/7/511:12
 */
public interface RequestMessageHandler extends MessageHandler {
    void dealRequestMessage(RequestMessageProto.RequestMessage requestMessage);
}

package com.ghj.android.core.observer;

import com.ghj.android.core.message.MessageManager;
import com.ghj.protocol.AckMessageProto;
import com.ghj.protocol.RequestMessageProto;
import com.ghj.common.base.Constant;

/**
 * @author gehj
 * @date 2019/7/1010:23
 */
public class LoginObserver implements Observer {

    LoginCallBack loginCallBack;

    public LoginObserver(LoginCallBack loginCallBack) {
        this.loginCallBack = loginCallBack;
    }

    @Override
    public void ackMessageListener(AckMessageProto.AckMessage ackMessage) {
        if (Constant.LOGIN_SUCCESS_CODE ==  ackMessage.getCode()) {
            MessageManager.getInstance().takeMessage();
            loginCallBack.success(ackMessage);
        }
        if (Constant.LOGIN_FAILURE_CODE == ackMessage.getCode()) {
            loginCallBack.failure(ackMessage);
        }
    }

    @Override
    public void requestMessageListener(RequestMessageProto.RequestMessage requestMessage) {
        return;
    }
}

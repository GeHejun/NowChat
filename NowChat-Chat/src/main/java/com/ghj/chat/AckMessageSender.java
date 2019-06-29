package com.ghj.chat;

import com.ghj.chat.protocol.AckMessageProto;
import com.ghj.chat.protocol.RequestMessageProto;
import com.ghj.common.dto.AbstractMessage;
import com.ghj.common.dto.MessageToGroup;
import com.ghj.common.dto.MessageToUser;
import com.ghj.common.exception.ChatException;
import com.ghj.common.mq.SendUtil;
import com.ghj.common.util.JSONUtil;
import com.ghj.common.util.OKHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class AckMessageSender implements Runnable{

    public AckMessageProto.AckMessage message;



    Session session;

    AbstractMessage abstractMessage;

    public AckMessageSender(AckMessageProto.AckMessage message) {
        this.message = message;
    }

    @Override
    public void run() {


    }
}

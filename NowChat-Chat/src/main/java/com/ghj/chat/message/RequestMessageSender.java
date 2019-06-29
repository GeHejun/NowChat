package com.ghj.chat.message;

import com.ghj.chat.constant.Route;
import com.ghj.chat.Session;
import com.ghj.chat.SessionManager;
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


/**
 * @author GeHejun
 * @date 2019/6/24 13:30
 */
public class RequestMessageSender implements Runnable {

    public RequestMessageProto.RequestMessage message;



    Session session;

    AbstractMessage abstractMessage;

    public RequestMessageSender(RequestMessageProto.RequestMessage message) {
        this.message = message;
    }

    @Override
    public void run() {
        switch (message.getMessageDirect()) {
            case PERSONAL:
                Integer sessionKey = message.getToUserId();
                session = SessionManager.getSession(sessionKey);
                if (session == null) {
                    throw new ChatException();
                }
                session.getChannel().writeAndFlush(message);
                abstractMessage = MessageToUser.builder().build();
                break;
            case GROUP:
                Integer toGroupId= message.getToGroupId();
                OKHttpUtil.get(Route.GET_GROUP_MEMBER + toGroupId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //重试三次如果不成功那么返回失败（还未实现）
                        MessageManager.getInstance().failureRequestMessage(message);
                    }
                    @Override
                    public void onResponse(Call call, Response response) {
                        String result = response.body().toString();
                        List<Integer> toIds = (List<Integer>)JSONUtil.jsonToList(result);
                        toIds.remove(message.getFromUserId());
                        toIds.forEach(id -> {
                            session = SessionManager.getSession(id);
                            if (session == null) {
                                throw new ChatException();
                            }
                            session.getChannel().writeAndFlush(message);
                        });
                        abstractMessage = MessageToGroup.builder().build();
                    }
                });
                break;
                default:
        }
        SendUtil.sendForQueue(abstractMessage);

    }
}

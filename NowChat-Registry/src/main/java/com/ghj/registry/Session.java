package com.ghj.registry;

import com.ghj.protocol.MessageProto;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author gehj
 * @date 2019/7/1 13:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private String ip;

    private int port;

    private MessageProto.Message.ConnectType connectType;

    private Channel channel;

    private int version;


}

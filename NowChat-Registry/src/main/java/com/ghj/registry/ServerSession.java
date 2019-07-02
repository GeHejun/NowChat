package com.ghj.registry;

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
public class ServerSession {

    private String ip;

    private int port;

    private Long machineSerialNumber;

    private Channel channel;

}

package com.ghj.registry;

import lombok.Data;

/**
 * @author gehj
 * @date 2019/7/1 13:32
 */
@Data
public class ServerSession {

    private String ip;

    private int port;

    private Long machineSerialNumber;

}

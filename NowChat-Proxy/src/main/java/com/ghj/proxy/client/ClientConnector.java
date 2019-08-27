package com.ghj.proxy.client;

import com.ghj.common.netty.BootstrapGenerator;
import com.ghj.common.netty.Register;
import io.netty.bootstrap.Bootstrap;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/27 11:46
 */
public class ClientConnector {

    Bootstrap client;

    public void start() {

        client = BootstrapGenerator.generateBootStrap(new ClientConnectHandler());



    }
}

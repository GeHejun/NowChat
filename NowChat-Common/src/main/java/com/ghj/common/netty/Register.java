package com.ghj.common.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ghj.common.base.Constant;
import com.ghj.common.exception.ServerException;
import com.ghj.common.util.PropertiesUtil;
import com.ghj.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.*;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ghj.common.base.Constant.CODE_KEY;

/**
 * @author gehj
 * @version 1.0
 * @description 注册器
 * @date 2019/8/27 13:02
 */
public class Register {

    private static volatile AtomicInteger RETRY_COUNT = new AtomicInteger(3);
    private static String registerIp = PropertiesUtil.getInstance().getValue(Constant.REGISTRY_IP, "127.0.0.1");
    ChannelFuture channelFuture;

    public ChannelFuture register(Connector connector, MessageProto.Message.ConnectType connectType, MessageProto.Message.MessageBehavior messageBehavior) {
        register(connector, connectType, messageBehavior, BootstrapGenerator.generateBootStrap(new SimpleChannelInboundHandler() {
            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
                RETRY_COUNT.getAndDecrement();
                if (RETRY_COUNT.intValue() > 0 && Objects.isNull(o)) {
                    reRegister(registerIp, connector, connectType, messageBehavior, channelFuture);
                }
                MessageProto.Message message = (MessageProto.Message) o;
                JSONObject result = (JSONObject) JSON.parse(message.getContent());
                if (RETRY_COUNT.intValue() > 0 &&  Constant.REGISTER_SUCCESS_CODE != Integer.parseInt(result.get(CODE_KEY).toString())) {
                    reRegister(registerIp, connector, connectType, messageBehavior, channelFuture);
                }
            }
        }));
        return channelFuture;
    }

    private void register(Connector connector, MessageProto.Message.ConnectType connectType, MessageProto.Message.MessageBehavior messageBehavior, Bootstrap client) {
        try {
            Integer registerPort = Integer.valueOf(PropertiesUtil.getInstance().getValue(Constant.REGISTRY_PORT, "9999"));
            channelFuture = client.connect((new InetSocketAddress(registerIp, registerPort)));
            channelFuture.addListener(future -> {
                if (!future.isSuccess()) {
                    connector.stop();
                    throw new ServerException();
                }

                //获取本地ip地址
//                InetSocketAddress inetSocketAddress = (InetSocketAddress) channelFuture.channel().localAddress();
//                String ip = inetSocketAddress.getAddress().getHostAddress();
                //获取ipv4的ip地址
                String ip = getLocalIpv4Address();
                reRegister(ip, connector, connectType, messageBehavior, channelFuture);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reRegister(String ip, Connector connector, MessageProto.Message.ConnectType connectType, MessageProto.Message.MessageBehavior messageBehavior, ChannelFuture channelFuture) {
        MessageProto.Message registerMessage =
                MessageProto.Message.newBuilder()
                        .setIp(ip)
                        .setPort(connector.getPort())
                        .setMessageBehavior(messageBehavior)
                        .setConnectType(connectType)
                        .build();
        channelFuture.channel().writeAndFlush(registerMessage);
    }

    public static String getLocalIpv4Address() throws SocketException {
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        String siteLocalAddress = null;
        while (ifaces.hasMoreElements()) {
            NetworkInterface iface = ifaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                String hostAddress = addr.getHostAddress();
                if (addr instanceof Inet4Address
                        && !addr.isLoopbackAddress()
                        && !hostAddress.startsWith("192.168")
                        && !hostAddress.startsWith("172.")
                        && !hostAddress.startsWith("169.")) {
                    if (addr.isSiteLocalAddress()) {
                        siteLocalAddress = hostAddress;
                    } else {
                        return hostAddress;
                    }
                }
            }
        }
        return siteLocalAddress == null ? "" : siteLocalAddress;
    }
}

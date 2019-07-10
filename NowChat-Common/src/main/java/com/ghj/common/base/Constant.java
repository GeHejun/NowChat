package com.ghj.common.base;

import com.ghj.common.util.MachineSerialNumber;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class Constant {

    public static final Long PING_ADD_TIME = 1000L;

    public static final String ENCRYPT_DECRYPT_KEY = "ENCRYPT_DECRYPT_KEY";

    public static final String USER_TOKEN_KEY = "USER_TOKEN_KEY";

    public static final String EXCHANGE_A = "MY-MQ-EXCHANGE_SAVE_MESSAGE";

    public static final String QUEUE_A = "QUEUE_SAVE_MESSAGE";

    public static final String ROUTING_KEY_A = "SPRING-BOOT-ROUTING_KEY_SAVE_MESSAGE";

    public static final String ON_LINE_USER_COUNT = "ON_LINE_USER_COUNT";

    public static final String ON_LINE_USER_LIST = "ON_LINE_USER_COUNT";

    public static final String SUCCESS = "成功";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String LOGIN_OUT_SUCCESS = "登出成功";

    public static final String PING_SUCCESS = "心跳包发送成功";

    public static final String MESSAGE_SEND_SUCCESS = "消息发送成功";

    public static final String ACK_SEND_SUCCESS = "回执消息发送成功";

    public static final String FAILURE = "失败";

    public static final String LOGIN_FAILURE = "登录失败";

    public static final String LOGIN_OUT_FAILURE = "登出失败";

    public static final String PING_FAILURE = "心跳包发送失败";

    public static final String MESSAGE_SEND_FAILURE = "信息发送失败";

    public static final String ACK_SEND_FAILURE = "回执消息发送失败";

    public static final String GROUP_MEMBER_REQUEST_FAILURE = "组成员请求失败";

    public static final String MESSAGE_RECEIVER_SUCCESS = "消息已经成功接收";

    public static final Long MACHINE_SERIAL_NUMBER = MachineSerialNumber.get();

    public static final String REGISTER_IP = "register.ip";

    public static final String REGISTER_PORT = "register.port";

    public static final String REGISTER_SUCCESS = "服务端注册成功";

    public static final String REGISTER_FAILURE = "服务端注册失败";

    public static final int SUCCESS_CODE = 2000;

    public static final int LOGIN_SUCCESS_CODE = 2001;

    public static final int PING_SUCCESS_CODE = 2002;

    public static final int MESSAGE_SEND_SUCCESS_CODE = 2003;

    public static final int ACK_SEND_SUCCESS_CODE = 2004;

    public static final int LOGIN_OUT_SUCCESS_CODE = 2005;

    public static final int MESSAGE_RECEIVER_SUCCESS_CODE = 2006;

    public static final int REGISTER_SUCCESS_CODE = 2007;

    public static final int FAILURE_CODE = 4000;

    public static final int LOGIN_FAILURE_CODE = 4001;

    public static final int PING_FAILURE_CODE = 4002;

    public static final int MESSAGE_SEND_FAILURE_CODE = 4003;

    public static final int ACK_SEND_FAILURE_CODE = 4004;

    public static final int LOGIN_OUT_FAILURE_CODE = 4005;

    public static final int GROUP_MEMBER_REQUEST_FAILURE_CODE = 4006;

    public static final int REGISTER_FAILURE_CODE = 4007;
}

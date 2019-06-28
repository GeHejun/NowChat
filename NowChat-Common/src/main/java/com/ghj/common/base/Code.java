package com.ghj.common.base;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public enum  Code {
    SUCCESS(2000, Constant.SUCCESS),
    LOGIN_SUCCESS(2001, Constant.LOGIN_SUCCESS),
    PING_SUCCESS(2002, Constant.PING_SUCCESS),
    MESSAGE_SEND_SUCCESS(2003, Constant.MESSAGE_SEND_SUCCESS),
    ACK_SEND_SUCCESS(2004, Constant.ACK_SEND_SUCCESS),
    FAILURE(4000, Constant.FAILURE),
    LOGIN_FAILURE(4001, Constant.LOGIN_FAILURE),
    PING_FAILURE(4002, Constant.PING_FAILURE),
    MESSAGE_SEND_FAILURE(4003, Constant.MESSAGE_SEND_FAILURE),
    ACK_SEND_FAILURE(4004, Constant.ACK_SEND_FAILURE);
    private int code;
    private String message;
     Code(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

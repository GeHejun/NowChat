package com.ghj.common.base;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public enum  Code {
    SUCCESS(10, Constant.SUCCESS),
    LOGIN_SUCCESS(11, Constant.LOGIN_SUCCESS),
    PING_SUCCESS(12, Constant.PING_SUCCESS),
    MESSAGE_SEND_SUCCESS(12, Constant.MESSAGE_SEND_SUCCESS),
    ACK_SEND_SUCCESS(12, Constant.ACK_SEND_SUCCESS),
    FAILURE(400, Constant.FAILURE);
    private int code;
    private String message;
     Code(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.ghj.common.base;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public enum  Code {
    SUCCESS(Constant.SUCCESS_CODE, Constant.SUCCESS),

    LOGIN_SUCCESS(Constant.LOGIN_SUCCESS_CODE, Constant.LOGIN_SUCCESS),

    PING_SUCCESS(Constant.PING_SUCCESS_CODE, Constant.PING_SUCCESS),

    MESSAGE_SEND_SUCCESS(Constant.MESSAGE_SEND_SUCCESS_CODE, Constant.MESSAGE_SEND_SUCCESS),

    ACK_SEND_SUCCESS(2004, Constant.ACK_SEND_SUCCESS),

    LOGIN_OUT_SUCCESS(2005, Constant.LOGIN_SUCCESS),

    MESSAGE_RECEIVER_SUCCESS(2006, Constant.MESSAGE_RECEIVER_SUCCESS),

    REGISTER_SUCCESS(2007, Constant.REGISTER_SUCCESS),

    FAILURE(4000, Constant.FAILURE),

    LOGIN_FAILURE(4001, Constant.LOGIN_FAILURE),

    PING_FAILURE(4002, Constant.PING_FAILURE),

    MESSAGE_SEND_FAILURE(4003, Constant.MESSAGE_SEND_FAILURE),

    ACK_SEND_FAILURE(4004, Constant.ACK_SEND_FAILURE),

    LOGIN_OUT_FAILURE(4005, Constant.LOGIN_FAILURE),

    GROUP_MEMBER_REQUEST_FAILURE(4006, Constant.GROUP_MEMBER_REQUEST_FAILURE),

    REGISTER_FAILURE(2007, Constant.REGISTER_FAILURE);

    private int code;
    private String message;

     Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}

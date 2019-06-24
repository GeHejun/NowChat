package com.ghj.common;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
public class BusinessException extends RuntimeException {
    int code;
    String message;
}

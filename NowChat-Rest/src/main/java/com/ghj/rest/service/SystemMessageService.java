package com.ghj.rest.service;

import com.ghj.rest.model.SystemMessage;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/9/6 14:44
 */
public interface SystemMessageService {
    /**
     * 插入系统消息
     * @param systemMessage
     */
    void insertSystemMessage(SystemMessage systemMessage);
}

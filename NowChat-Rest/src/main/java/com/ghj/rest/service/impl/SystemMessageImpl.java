package com.ghj.rest.service.impl;

import com.ghj.rest.dao.SystemMessageMapper;
import com.ghj.rest.model.SystemMessage;
import com.ghj.rest.service.SystemMessageService;

import javax.annotation.Resource;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/9/6 14:46
 */
public class SystemMessageImpl implements SystemMessageService {

    @Resource
    SystemMessageMapper systemMessageMapper;


    @Override
    public void insertSystemMessage(SystemMessage systemMessage) {
        systemMessageMapper.insert(systemMessage);
    }
}

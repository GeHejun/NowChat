package com.ghj.rest.service.impl;

import com.ghj.rest.dao.GroupMessageMapper;
import com.ghj.rest.model.GroupMessage;
import com.ghj.rest.service.GroupMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gehj
 * @date 2019/6/2812:55
 */
@Service
public class GroupMessageServiceImpl implements GroupMessageService {

    @Resource
    GroupMessageMapper groupMessageMapper;

    @Override
    public void insert(GroupMessage groupMessage) {
        groupMessageMapper.insert(groupMessage);
    }
}

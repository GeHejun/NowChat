package com.ghj.rest.service.impl;

import com.ghj.common.dto.response.HistoryMessage;
import com.ghj.rest.dao.GroupMessageMapper;
import com.ghj.rest.model.GroupMessage;
import com.ghj.rest.service.GroupMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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


    @Override
    public HistoryMessage queryHistoryGroupMessageListForPage(Integer toGroupId, Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<GroupMessage> groupMessageList = groupMessageMapper.selectGroupMessageByToGroupId(toGroupId);
        PageInfo<GroupMessage> pageInfo = new PageInfo<>(groupMessageList);
        HistoryMessage historyMessage = new HistoryMessage();
        historyMessage.setPageNum(pageInfo.getPageNum());
        historyMessage.setPageSize(pageInfo.getPageSize());
        historyMessage.setTotal(pageInfo.getTotal());
        historyMessage.setData(groupMessageList);
        return historyMessage;
    }
}

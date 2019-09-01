package com.ghj.rest.service;

import com.ghj.common.dto.response.HistoryMessage;
import com.ghj.rest.model.GroupMessage;

/**
 * @author gehj
 * @date 2019/6/2812:55
 */
public interface GroupMessageService {

    void insert(GroupMessage groupMessage);

    HistoryMessage queryHistoryGroupMessageListForPage(Integer toGroupId, Integer pageIndex, Integer pageSize);
}

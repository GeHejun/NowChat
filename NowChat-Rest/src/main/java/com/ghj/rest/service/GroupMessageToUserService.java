package com.ghj.rest.service;

import com.ghj.common.dto.response.GroupMessageToUserResponse;
import com.ghj.common.dto.response.GroupToUserResponse;
import com.ghj.common.dto.response.HistoryMessage;
import com.ghj.rest.model.GroupMessageToUser;

import java.util.List;

public interface GroupMessageToUserService {

    void insert(GroupMessageToUser groupMessageToUser);

    List<GroupMessageToUserResponse> listMessageByToUserIdAndStatus(Integer toUserId, Boolean status);


}

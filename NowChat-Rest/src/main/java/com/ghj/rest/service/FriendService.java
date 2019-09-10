package com.ghj.rest.service;

import com.ghj.common.dto.response.FriendResponse;

import java.util.List;

/**
 * @author gehj
 * @date 2019/6/2511:36
 */
public interface FriendService {
    /**
     * 根据用户id查询好友列表
     * @param userId
     * @return
     */
    List<FriendResponse> listFriendsByUserId(Integer userId);

    Boolean agreeFriend(Long validationMessageId, Integer fromUserId, Integer fromFriendGroupId, Integer toUserId, Integer toFriendGroupId, String newFriendGroupName);

    Boolean refuseFriend(Long validationMessageId);
}

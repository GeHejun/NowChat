package com.ghj.rest.service;

import com.ghj.rest.dto.FriendResponse;

import java.util.List;

/**
 * @author gehj
 * @date 2019/6/2511:36
 */
public interface FriendService {
    List<FriendResponse> listFriendsByUserId(Integer userId);
}

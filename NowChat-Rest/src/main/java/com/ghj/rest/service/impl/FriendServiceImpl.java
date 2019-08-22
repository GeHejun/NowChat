package com.ghj.rest.service.impl;

import com.ghj.rest.dao.FriendMapper;
import com.ghj.common.dto.response.FriendResponse;
import com.ghj.rest.model.Friend;
import com.ghj.rest.service.FriendService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gehj
 * @date 2019/6/2511:36
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    FriendMapper friendMapper;

    @Override
    public List<FriendResponse> listFriendsByUserId(Integer userId) {
        List<Friend> friendList = friendMapper.listFriendListByUserId(userId);
        List<FriendResponse> friendResponseList = new ArrayList<>(friendList.size());
        friendList.stream().forEach(friend -> {
            FriendResponse friendResponse = new FriendResponse();
            BeanUtils.copyProperties(friend, friendResponse);
            friendResponseList.add(friendResponse);
        });
        return friendResponseList;
    }
}

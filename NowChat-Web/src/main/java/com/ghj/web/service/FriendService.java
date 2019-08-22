package com.ghj.web.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.FriendResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 10:17
 */
@FeignClient(name = "rest")
public interface FriendService {

    /**
     * 查询好友列表
     * @param userId
     * @return
     */
    @RequestMapping("/queryFriendList")
    @ResponseBody
    Result<List<FriendResponse>> queryFriendList(@Valid Integer userId);
}

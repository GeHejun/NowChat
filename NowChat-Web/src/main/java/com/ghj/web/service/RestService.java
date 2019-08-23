package com.ghj.web.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.FriendGroupResponse;
import com.ghj.common.dto.response.FriendResponse;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.common.dto.response.UserStateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 11:03
 */
@FeignClient(name = "rest")
public interface RestService {

    /**
     * 查询好友所在群组信息
     * @param id
     * @return
     */
    @RequestMapping("/queryGroupById")
    Result<FriendGroupResponse> queryGroupById(@NotNull Integer id);

    /**
     * 查询好友列表
     * @param userId
     * @return
     */
    @RequestMapping("/queryFriendList")
    @ResponseBody
    Result<List<FriendResponse>> queryFriendList(@Valid Integer userId);

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    @ResponseBody
    Result<UserResponse> queryUser(@NotNull Integer id);

    /**
     * 查询状态信息
     * @param id
     * @return
     */
    @RequestMapping("/")
    Result<UserStateResponse> queryUserStateById(@NotNull Integer id);
}

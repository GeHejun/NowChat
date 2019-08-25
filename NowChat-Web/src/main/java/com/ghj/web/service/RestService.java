package com.ghj.web.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.FriendGroupResponse;
import com.ghj.common.dto.response.FriendResponse;
import com.ghj.common.dto.response.UserResponse;
import com.ghj.common.dto.response.UserStateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    @RequestMapping("/friendGroup/queryGroupById")
    @ResponseBody
    Result<FriendGroupResponse> queryGroupById(@NotNull @RequestParam("id") Integer id);

    /**
     * 查询好友列表
     * @param userId
     * @return
     */
    @RequestMapping("/friend/queryFriendList")
    @ResponseBody
    Result<List<FriendResponse>> queryFriendList(@Valid @RequestParam("userId") Integer userId);

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/queryUser")
    @ResponseBody
    Result<UserResponse> queryUser(@NotNull @RequestParam("id") Integer id);

    /**
     * 查询状态信息
     * @param id
     * @return
     */
    @RequestMapping("/state/queryUserStateById")
    @ResponseBody
    Result<UserStateResponse> queryUserStateById(@NotNull @RequestParam("id") Integer id);

    /**
     * 登录验证
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    Result<UserResponse> login(@NotNull @RequestParam("loginName") String loginName, @NotNull @RequestParam("password") String password);
}

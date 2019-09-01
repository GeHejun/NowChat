package com.ghj.web.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.request.UserRequest;
import com.ghj.common.dto.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/groupToUser/findGroupByUserId")
    @ResponseBody
    Result<List<GroupToUserResponse>> findGroupsByUserId(@NotNull @RequestParam("userId") Integer userId);

    @RequestMapping("/groupToUser/findUserIdByGroupId")
    @ResponseBody
    Result<List<Integer>> findUserIdByGroupId(@RequestParam("groupId") @NotNull Integer groupId);

    @RequestMapping("/userGroup/findGroupById")
    @ResponseBody
    Result<UserGroupResponse> findGroupById(@RequestParam("id") @NotNull Integer id);
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

    @RequestMapping("/groupMessageToUser/listGroupMessageByToUserIdAndStatus")
    @ResponseBody
    Result<List<GroupMessageToUserResponse>> listGroupMessageByToUserIdAndStatus(@RequestParam("toUserId") @NotNull Integer toUserId, @RequestParam(value = "status", defaultValue = "false") Boolean status);

    @RequestMapping("/message/queryMessageWithToUserIdAndStatus")
    @ResponseBody
    Result<List<MessageResponse>> queryMessageWithToUserIdAndStatus(@NotNull @RequestParam("toUserId") Integer toUserId, @RequestParam(defaultValue = "false") @NotNull Boolean status);

    @RequestMapping(value = "/user/checkUser")
    @ResponseBody
    Result<Boolean> checkUser(@NotNull @RequestParam("loginName") String loginName);

    @RequestMapping(value = "/user/register")
    @ResponseBody
    Result<UserResponse> register(@RequestBody UserRequest userRequest);

    @RequestMapping("/message/queryHistoryMessageListForPage")
    @ResponseBody
    Result<HistoryMessage<MessageResponse>> queryHistoryMessageListForPage(@NotNull @RequestParam("toUserId") Integer toUserId,
                                   @RequestParam(defaultValue = "1") Integer pageIndex,
                                   @RequestParam(defaultValue = "10") Integer pageSize);

    @RequestMapping("/groupMessage/queryHistoryGroupMessageListForPage")
    @ResponseBody
    Result<HistoryMessage> queryHistoryGroupMessageListForPage(@NotNull @RequestParam("toUserId") Integer toGroupId,
                                                                      @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                      @RequestParam(defaultValue = "10") Integer pageSize);

}

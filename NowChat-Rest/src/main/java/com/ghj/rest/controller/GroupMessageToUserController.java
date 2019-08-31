package com.ghj.rest.controller;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.GroupMessageToUserResponse;
import com.ghj.common.dto.response.GroupToUserResponse;
import com.ghj.rest.service.GroupMessageToUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
@Controller
@RequestMapping("/groupMessageToUser")
public class GroupMessageToUserController {

    @Resource
    GroupMessageToUserService groupMessageToUserService;



    @RequestMapping("/listMessageByToUserIdAndStatus")
    @ResponseBody
    public Result<List<GroupMessageToUserResponse>> listMessageByToUserIdAndStatus(@RequestParam("toUserId") @NotNull Integer toUserId, @RequestParam(value = "status", defaultValue = "false") Boolean status) {
        return Result.defaultSuccess(groupMessageToUserService.listMessageByToUserIdAndStatus(toUserId, status));
    }
}
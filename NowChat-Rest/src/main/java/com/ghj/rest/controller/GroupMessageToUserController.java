package com.ghj.rest.controller;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.GroupMessageToUserResponse;
import com.ghj.common.dto.response.GroupToUserResponse;
import com.ghj.common.dto.response.HistoryMessage;
import com.ghj.rest.service.GroupMessageToUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author GeHejun
 */
@Controller
@RequestMapping("/groupMessageToUser")
public class GroupMessageToUserController {

    @Resource
    GroupMessageToUserService groupMessageToUserService;



    @RequestMapping("/listGroupMessageByToUserIdAndStatus")
    @ResponseBody
    public Result<List<GroupMessageToUserResponse>> listGroupMessageByToUserIdAndStatus(@RequestParam("toUserId") @NotNull Integer toUserId, @RequestParam(value = "status", defaultValue = "false") Boolean status) {
        return Result.defaultSuccess(groupMessageToUserService.listMessageByToUserIdAndStatus(toUserId, status));
    }

    /**
     * 群消息标记为已读
     * @param groupId
     * @param toUserId
     * @return
     */
    @RequestMapping("/readGroupMessage")
    @ResponseBody
    public Result<Boolean> readGroupMessage(@NotNull @RequestParam("groupId") Integer groupId,@NotNull @RequestParam("toUserId") Integer toUserId) {
        return Result.defaultSuccess(groupMessageToUserService.readGroupMessage(groupId, toUserId));
    }

}

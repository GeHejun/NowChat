package com.ghj.rest.controller;

import com.ghj.common.base.Result;
import com.ghj.rest.dto.MessageResponse;
import com.ghj.rest.service.MessageService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author gehj
 * @date 2019/6/2517:43
 */
@RequestMapping("/message")
@Controller
public class MessageController {

    @Resource
    MessageService messageService;

    @RequestMapping("/queryMessageList")
    @ResponseBody
    public Result queryMessagePage(@NotNull Integer userId,
                                   @RequestParam(defaultValue = "1") Integer pageIndex,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MessageResponse> pageInfo = messageService.listMessagePage(userId, pageIndex, pageSize);
        return Result.defaultSuccess(pageInfo);
    }

    @RequestMapping("/queryMessage")
    @ResponseBody
    public Result queryMessagePage(@NotNull Integer id) {
        MessageResponse messageResponse = messageService.queryMessageById(id);
        return Result.defaultSuccess(messageResponse);
    }

}
package com.ghj.web.controller;

import com.ghj.common.base.Result;
import com.ghj.web.service.MainFrameService;
import com.ghj.web.vo.MainFrameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author gehj
 * @version 1.0
 * @description 主视图数据
 * @date 2019/8/22 10:09
 */
@RestController
@RequestMapping("/index")
public class MainFrameController {


    @Autowired
    MainFrameService mainFrameService;

    @RequestMapping
    public Result<MainFrameVO> initMainFrame(@NotNull Integer id) {
        MainFrameVO mainFrameVO = mainFrameService.initMainFrame(id);
        return Result.defaultSuccess(mainFrameVO);
    }
}

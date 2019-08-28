package com.ghj.web.controller;

import com.ghj.web.service.MainFrameService;
import com.ghj.web.vo.MainFrameVO;
import com.ghj.web.vo.ResultVO;
import com.ghj.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author gehj
 * @version 1.0
 * @description 主视图数据
 * @date 2019/8/22 10:09
 */
@Controller
@RequestMapping("/index")
public class MainFrameController {


    @Autowired
    MainFrameService mainFrameService;

    /**
     * 暂时写死用id 以后改成token
     */
    @RequestMapping("/initMainFrame")
    @ResponseBody
    public ResultVO<MainFrameVO> initMainFrame(@NotNull @RequestParam("id") Integer id) {
        MainFrameVO mainFrameVO = mainFrameService.initMainFrame(id);
        return ResultVO.defaultSuccess(mainFrameVO);
    }

    @RequestMapping
    @ResponseBody
    public ResultVO<List<UserVO>> initMembers(@NotNull @RequestParam("groupId") Integer groupId) {
        List<UserVO> memberVOList = mainFrameService.initMembers(groupId);
        return ResultVO.defaultSuccess(memberVOList);
    }
}

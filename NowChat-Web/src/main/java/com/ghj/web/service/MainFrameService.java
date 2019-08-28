package com.ghj.web.service;

import com.ghj.web.vo.MainFrameVO;
import com.ghj.web.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 10:38
 */
@Service
public interface MainFrameService {

    /**
     * 初始化主面板数据
     * @param id
     * @return
     */
    MainFrameVO initMainFrame(Integer id);

    List<UserVO> initMembers(Integer groupId);
}

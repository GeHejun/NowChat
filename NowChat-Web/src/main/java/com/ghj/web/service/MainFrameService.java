package com.ghj.web.service;

import com.ghj.web.vo.MainFrameVO;
import org.springframework.stereotype.Service;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/22 10:38
 */
@Service
public interface MainFrameService {

    MainFrameVO initMainFrame(Integer id);
}

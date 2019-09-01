package com.ghj.web.service;

import com.ghj.web.vo.*;
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

    MemberVO initMembers(Integer groupId);

    List<MessageVO> initOffLineMessages(Integer toUserId, Boolean status);

    HistoryMessageVO initHistoryMessage(Integer fromUserId, Integer toUserId, String type, Integer pageIndex, Integer pageSize);
}

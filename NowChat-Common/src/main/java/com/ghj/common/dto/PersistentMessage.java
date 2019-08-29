package com.ghj.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/29 18:59
 */
@Data
@Builder
public class PersistentMessage {

    private Long id;

    /**
     * 接收状态
     */
    private Boolean status;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 消息类型ID
     */
    private Integer messageTypeId;

    /**
     *
     */
    private Integer fromUserId;

    /**
     * 消息内容
     */
    private String postMessage;

    private Integer toGroupId;

    private Integer toUserId;

    private List<Integer> onLineUserIds;

    private List<Integer> offLineUserIds;

}

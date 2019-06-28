package com.ghj.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author gehj
 * @date 2019/6/2811:34
 */
@Data
public class AbstractMessage  {

    private Integer id;

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
}

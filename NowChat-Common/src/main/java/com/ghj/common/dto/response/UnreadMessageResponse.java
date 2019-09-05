package com.ghj.common.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class UnreadMessageResponse {

    private Integer fromUserId;

    private Integer toGroupId;

    private Integer toUserId;

    private String type;

    private Date sendTime;

    private Integer count;

    private String content;

}

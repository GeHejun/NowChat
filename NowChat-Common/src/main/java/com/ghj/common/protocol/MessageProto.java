package com.ghj.common.protocol;

import com.ghj.common.ClientBehavior;
import lombok.Data;

@Data
public class MessageProto {

    private Integer fromUserId;

    private Integer toUserId;

    private Integer messageTypeId;

    private ClientBehavior clientBehavior;

    private String content;

}

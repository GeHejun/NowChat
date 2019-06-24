package com.ghj.common.protocol;

import com.ghj.common.ClientBehavior;
import com.ghj.common.MessageDirect;
import lombok.Builder;
import lombok.Data;

/**
 * @author GeHejun
 * @date 2019-06-24
 */
@Data
@Builder
public class MessageProto {

    private Integer fromUserId;

    private Integer toUserId;

    private Integer messageTypeId;

    private ClientBehavior clientBehavior;

    private String content;

    private MessageDirect messageDirect;

    private Integer toGroupId;

}

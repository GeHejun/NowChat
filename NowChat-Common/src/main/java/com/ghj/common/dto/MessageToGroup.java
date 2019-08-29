package com.ghj.common.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author gehj
 * @date 2019/6/2811:34
 */
@Data
@Builder
public class MessageToGroup extends AbstractMessage {
    private Integer toGroupId;

    private Integer toUserId;
}

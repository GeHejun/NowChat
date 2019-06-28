package com.ghj.common.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author gehj
 * @date 2019/6/2811:33
 */
@Data
@Builder
public class MessageToUser extends AbstractMessage{

    private Integer toUserId;
}

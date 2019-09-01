package com.ghj.web.vo;

import com.ghj.common.dto.response.MessageResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HistoryMessageVO {
    Integer size;
    Integer pageNum;
    Integer pageSize;
    List<MessageVO> data;
}

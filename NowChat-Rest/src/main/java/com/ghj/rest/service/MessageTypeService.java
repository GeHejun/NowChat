package com.ghj.rest.service;

import com.ghj.common.base.Code;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/9/6 15:46
 */
public interface MessageTypeService {

    Integer queryMessageTypeByName(String name);

    String queryMessageTypeNameById(Integer id);
}

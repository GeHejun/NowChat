package com.ghj.rest.dao;

import com.ghj.rest.model.SystemMessage;
import org.springframework.stereotype.Repository;

/**
 * SystemMessageMapper继承基类
 * @author GeHejun
 */
@Repository
public interface SystemMessageMapper extends MyBatisBaseDao<SystemMessage, Integer> {
}
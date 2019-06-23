package com.ghj.rest.dao;

import com.ghj.rest.model.GoupMessage;
import org.springframework.stereotype.Repository;

/**
 * GoupMessageMapper继承基类
 */
@Repository
public interface GoupMessageMapper extends MyBatisBaseDao<GoupMessage, Integer> {
}
package com.ghj.rest.dao;

import com.ghj.rest.model.Nation;
import org.springframework.stereotype.Repository;

/**
 * NationMapper继承基类
 */
@Repository
public interface NationMapper extends MyBatisBaseDao<Nation, Integer> {
}
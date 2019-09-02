package com.ghj.rest.dao;

import com.ghj.rest.model.UserGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * UserGroupMapper继承基类
 * @author GeHejun
 */
public interface UserGroupMapper extends MyBatisBaseDao<UserGroup, Integer> {
    /**
     * 通过群名称查询群组
     * @param name
     * @return
     */
    UserGroup selectGroupByName(@Param("name") String name);
}
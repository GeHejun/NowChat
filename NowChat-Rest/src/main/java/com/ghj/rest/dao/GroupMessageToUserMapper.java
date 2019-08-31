package com.ghj.rest.dao;

import com.ghj.rest.model.GroupMessageToUser;
import com.ghj.rest.model.GroupToUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GroupMessageToUserMapper继承基类
 */
public interface GroupMessageToUserMapper extends MyBatisBaseDao<GroupMessageToUser, Integer> {

    List<GroupMessageToUser> selectMessageByToUserIdAndStatus(@Param("toUserId") Integer toUserId, @Param("status") Boolean status);
}
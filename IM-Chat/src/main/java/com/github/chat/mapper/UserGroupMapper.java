package com.github.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.UserGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper extends BaseMapper<UserGroup> {

    int selectCountByGroupId(@Param("groupId") int groupId);

    List<Integer> selectUserIdByGroupId(@Param("groupId") int groupId);
}
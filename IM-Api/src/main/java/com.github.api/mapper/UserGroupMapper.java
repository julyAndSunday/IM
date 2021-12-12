package com.github.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.UserGroup;
import org.apache.ibatis.annotations.Param;

public interface UserGroupMapper extends BaseMapper<UserGroup> {

    int selectCountByGroupId(@Param("groupId") int groupId);
}
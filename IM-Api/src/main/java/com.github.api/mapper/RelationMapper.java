package com.github.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.Relation;
import org.apache.ibatis.annotations.Param;

public interface RelationMapper extends BaseMapper<Relation> {

    int selectRelation(@Param("userId") int userId , @Param("friendId") int friendId);
}
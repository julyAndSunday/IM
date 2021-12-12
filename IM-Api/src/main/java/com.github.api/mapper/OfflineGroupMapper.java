package com.github.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.OfflineGroup;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OfflineGroupMapper extends BaseMapper<OfflineGroup> {
    List<OfflineGroup> selectGroupByUserId(@Param("userId") int userId, @Param("lastLoginTime") Date lastLoginTime);


    void updateMsgsByUserId(@Param("toId") int toId, @Param("lastMsgId") int lastMsgId);
}
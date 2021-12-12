package com.github.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.OfflineMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfflineMsgMapper extends BaseMapper<OfflineMsg> {
    List<OfflineMsg> selectMsgsByUserId(@Param("userId") int userId);

    void deleteMsgsByUserId(@Param("userId") int userId, @Param("lastMsgId") int lastMsgId);
}
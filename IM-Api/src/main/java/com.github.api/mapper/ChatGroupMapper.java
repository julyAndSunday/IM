package com.github.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.ChatGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatGroupMapper extends BaseMapper<ChatGroup> {

    List<ChatGroup> selectByUserId(@Param("userId") int userId);

    List<Integer> selectIdByUserId(int id);

}
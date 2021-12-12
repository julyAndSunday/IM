package com.github.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.Moments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MomentsMapper extends BaseMapper<Moments> {
    int deleteByPrimaryKey(String id);

    int insert(Moments record);

    int insertSelective(Moments record);

    Moments selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Moments record);

    int updateByPrimaryKey(Moments record);

    List<Moments> selectByUserId(@Param("userId") int userId);

    List<Moments> selectFriendsMoments(@Param("userId") int userId);
}
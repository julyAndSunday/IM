package com.github.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-03-26 21:43
 **/
public interface UserMapper extends BaseMapper<User> {
    void updateLastLoginTimeByUserId(@Param("userId") int userId);
}

package com.github.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    void addFriend(@Param("userId") int userId, @Param("friendId") int friendId);

    User selectByUsername(@Param("username") String username);

    int selectIdByUsername(@Param("username") String username);

    List<User> selectFriends(@Param("id") int id);

    List<User> selectByUsernameNoWithPassword(String username);

    Date selectLastLoginTime(@Param("userId") int userId);


    Date updateLastLogin(@Param("userId") int userId, @Param("date") Date date);

    @Select("select id from user where username =#{username} and password = #{password}")
    Integer selectIdByUsernameAndPassword(@Param("username")String username,@Param("password")String password);


}
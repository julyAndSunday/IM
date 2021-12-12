package com.github.chat.service;

import com.github.chat.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-12-08 22:09
 **/
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void updateLastLoginTimeByUserId(int userId){
        userMapper.updateLastLoginTimeByUserId(userId);
    }
}

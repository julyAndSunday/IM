package com.github.chat.service;

import com.github.chat.mapper.ChatGroupMapper;
import com.github.chat.mapper.UserGroupMapper;
import com.github.common.entity.ChatGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-12-08 21:46
 **/
@Service
public class GroupService {

    @Resource
    private ChatGroupMapper chatGroupMapper;
    @Resource
    private UserGroupMapper userGroupMapper;

    public List<Integer> getUserIdsByGroupId(int id){
        return userGroupMapper.selectUserIdByGroupId(id);
    }
}

package com.github.api.service;

import com.github.api.mapper.ChatGroupMapper;
import com.github.api.mapper.UserGroupMapper;
import com.github.common.entity.ChatGroup;
import com.github.common.entity.UserGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-08-24 14:46
 **/
@Service
public class ChatGroupService {
    @Resource
    private ChatGroupMapper chatGroupMapper;
    @Resource
    private UserGroupMapper userGroupMapper;

    public Integer createGroup(ChatGroup chatGroup){
        chatGroupMapper.insert(chatGroup);
        return chatGroup.getId();
    }

    public List<ChatGroup> getGroupByUserId(int userId) {
        return chatGroupMapper.selectByUserId(userId);
    }

    public List<Integer> getGroupIdByUserId(int userId) {
        return chatGroupMapper.selectIdByUserId(userId);
    }


}

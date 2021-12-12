package com.github.api.service;

import com.github.api.mapper.UserGroupMapper;
import com.github.common.entity.UserGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-08-24 15:13
 **/
@Service
public class UserGroupService {

    @Resource
    private UserGroupMapper userGroupMapper;

    public int joinGroup(String groupId,int userId){
        return userGroupMapper.insert(new UserGroup(userId,groupId));
    }

    public void joinGroup(String groupId, List<Integer> usersId){
        for (int userId : usersId){
            userGroupMapper.insert(new UserGroup(userId,groupId));
        }
    }

    public Integer getGroupNum(int groupId){
        return userGroupMapper.selectCountByGroupId(groupId);
    }
}

package com.github.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * user_group
 * @author 
 */
@Data
public class UserGroup  {
    private Integer id;

    private Integer userId;

    private String groupId;

    private Date joinTime;

    public UserGroup(Integer userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
        joinTime = new Date();
    }

    public UserGroup() {
    }
}
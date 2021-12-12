package com.github.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * chat_group
 * @author 
 */
@Data
public class ChatGroup {
    private Integer id;
    /**
     * 群名称
     */
    private String name;

    /**
     * 创建用户
     */
    private Integer belong;

    /**
     * 创建时间
     */
    private Date createTime;

    public ChatGroup() {
    }

    public ChatGroup(String name, Integer belong) {
        this.name = name;
        this.belong = belong;
        createTime = new Date();
    }
}
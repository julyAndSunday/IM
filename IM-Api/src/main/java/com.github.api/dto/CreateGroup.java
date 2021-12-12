package com.github.api.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-08-24 11:04
 **/
@Data
public class CreateGroup {

    private int id;
    //创建者id
    private int userId;
    //群昵称
    private String name;
    //建群时邀请的用户id
    private List<Integer> friendId;
    private Date date;
}

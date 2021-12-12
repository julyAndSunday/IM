package com.github.common.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-05-02 20:28
 **/
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }
}

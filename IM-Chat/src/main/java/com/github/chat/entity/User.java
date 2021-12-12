package com.github.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-02-19 22:18
 **/
@Getter
@Setter
public class User implements Serializable {
    private long id;
    private String username;
    private String password;
    private String nickname;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

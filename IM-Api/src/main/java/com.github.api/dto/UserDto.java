package com.github.api.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-08-25 20:54
 **/
@Data
public class UserDto {
    private int id;
    private String username;
    private String token;

    public UserDto(int id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }
}

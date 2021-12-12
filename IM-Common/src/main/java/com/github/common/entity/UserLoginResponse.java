package com.github.common.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 11:11
 **/
@Data
public class UserLoginResponse {
    private RegisterService registerService;
    private String token;

}

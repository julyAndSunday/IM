package com.github.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 18:03
 **/
@SpringBootApplication
@MapperScan("com.github.chat.mapper")
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class);
    }
}

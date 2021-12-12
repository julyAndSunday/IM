package com.github.client.store;

import io.netty.channel.Channel;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 20:05
 **/
public class UserInfo {
    private  int id;
    private  String username;
    private  String token;
    private Channel channel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

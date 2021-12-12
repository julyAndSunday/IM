package com.github.common.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 10:28
 **/
public class Peer {
    private String ip;
    private int port;

    public Peer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Peer() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

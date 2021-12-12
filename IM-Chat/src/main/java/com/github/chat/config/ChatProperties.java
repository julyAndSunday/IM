package com.github.chat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-12-08 11:12
 **/
@ConfigurationProperties(prefix = "chat")
public class ChatProperties {


    private String serviceName;
    private String ip;
    private int port;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}

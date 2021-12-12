package com.github.chat.config;

import com.github.chat.server.IMService;
import com.github.chat.server.InternalService;
import com.github.chat.server.handler.InternalHandler;
import com.github.chat.server.handler.ServerHandler;
import com.github.common.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-12-08 20:30
 **/
@Configuration
@EnableConfigurationProperties(ChatProperties.class)
public class ChatConfiguration {
    @Autowired
    private ChatProperties chatProperties;

    @Bean
    public IMService imService(){
        return new IMService(chatProperties.getServiceName(),chatProperties.getIp(),chatProperties.getPort());
    }

    @Bean
    public InternalService internalService(){
        return new InternalService(chatProperties.getServiceName());
    }

    @Bean
    public ServerHandler serverHandler(){
        return new ServerHandler(chatProperties.getServiceName());
    }

    @Bean
    public InternalHandler internalHandler(){
        return new InternalHandler();
    }

    @Bean
    public SnowFlake snowFlake(){
        String serviceName = chatProperties.getServiceName();
        int machinId =  Integer.parseInt(serviceName.substring(serviceName.length()-1));
        return new SnowFlake(1,machinId);
    }
}

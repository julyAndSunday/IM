package com.github.chat.bootstap;

import com.github.chat.config.ChatProperties;
import com.github.common.utils.ServiceUtil;
import com.github.common.utils.zookeeper.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import com.github.chat.conn.ConnectManager;
import com.github.chat.server.IMService;
import com.github.chat.server.InternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-29 23:17
 **/
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    @Autowired
    private IMService imService;
    @Autowired
    private InternalService internalService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        internalService.start(countDownLatch);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imService.start();
        logger.info("{}:start",imService.getServiceName());
    }
}

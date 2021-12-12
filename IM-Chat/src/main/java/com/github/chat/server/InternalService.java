package com.github.chat.server;

import com.github.chat.conn.ConnectManager;
import com.github.common.codec.MsgDecoder;
import com.github.common.codec.MsgEncoder;
import com.github.common.entity.Peer;
import com.github.common.messgae.ChatMessage;
import com.github.common.entity.RegisterService;
import com.github.chat.server.handler.InternalHandler;
import com.github.common.utils.ServiceUtil;
import com.github.common.utils.zookeeper.ZkConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-20 10:06
 **/
public class InternalService {

    private String serviceName;
    private Bootstrap bootstrap;
    @Autowired
    private InternalHandler internalHandler;

    public InternalService(String serviceName) {
        this.serviceName = serviceName;
    }

    public void start(CountDownLatch countDownLatch) {
        new Thread(() -> {
            bootstrap = new Bootstrap();
            bootstrap.group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MsgDecoder())
                                    .addLast(new MsgEncoder())
                                    .addLast(internalHandler);
                        }
                    });
            ServiceUtil.watchPathChildrenNode(ZkConstant.ZK_REGISTER, new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                    PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
                    switch (type) {
                        case CHILD_ADDED:
                            System.out.println("update");
                            update();
                            break;
                        case CHILD_UPDATED:
                            break;
                        case CHILD_REMOVED:
                            break;
                    }
                }
            });
            countDownLatch.countDown();
        }).start();
    }

    //更新内部连接
    public void update() {
        List<RegisterService> allService = ServiceUtil.getAllService();
        for (RegisterService service : allService) {
            String serviceName = service.getName();
            if (!serviceName.equals(this.serviceName) && !ConnectManager.containService(serviceName)) {
                Peer peer = service.getPeer();
                ChannelFuture future = bootstrap.connect(peer.getIp(), peer.getPort());
                future.addListener((ChannelFutureListener) future1 -> {
                    if (future1.isSuccess()) {
                        ConnectManager.putService_channel(serviceName, future1.channel());
                    }
                });
            }
        }
    }

    public static ChannelFuture send(String serviceName, ChatMessage chatMessage) {
        ChannelFuture future = null;
        Channel internalChannel = ConnectManager.getByService(serviceName);
        if (internalChannel != null && internalChannel.isActive()) {
            future = internalChannel.writeAndFlush(chatMessage);
        }
        return future;
    }

}

package com.github.chat.server;

import com.github.chat.conn.ConnectManager;
import com.github.chat.server.handler.ServerHandler;
import com.github.common.codec.MsgDecoder;
import com.github.common.codec.MsgEncoder;
import com.github.common.entity.Peer;
import com.github.common.utils.ServiceUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-02-19 21:34
 **/
public class IMService {
    private String serviceName;
    private String ip;
    private int port;

    public IMService(String serviceName, String ip, int port) {
        this.serviceName = serviceName;
        this.ip = ip;
        this.port = port;
    }
    @Autowired
    private ServerHandler serverHandler;

    public String getServiceName() {
        return serviceName;
    }

    public void start() {
        new Thread(() -> {
            //注册服务
            ServiceUtil.registerService(serviceName, new Peer(ip, port));
            NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
            NioEventLoopGroup workGroup = new NioEventLoopGroup();

            //创建服务端启动对象 配置参数
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 128) //线程队列得到的连接数
                        .childOption(ChannelOption.SO_KEEPALIVE, true) //保存活动连接状态
                        // .handler(null)  //对应bossGroup
                        .childHandler(new ChannelInitializer<SocketChannel>() {  //对应workGroup 创建一个通道测试对象
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline()
                                        .addLast(new MsgDecoder())
                                        .addLast(new MsgEncoder())
                                        .addLast(serverHandler);
                            }
                        });

                //绑定一个端口并且同步
                //启动服务器
                ChannelFuture cf = bootstrap.bind(ip, port).sync();
                //对关闭通道进行监听
                cf.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }).start();
    }
}

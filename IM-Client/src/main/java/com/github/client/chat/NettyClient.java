package com.github.client.chat;

import com.github.client.chat.handler.ClientHandler;
import com.github.common.codec.MsgDecoder;
import com.github.common.codec.MsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @Description:
 * @Author: July
 * @Date: 2021-02-23 22:58
 **/
public class NettyClient {
    private String ip;
    private int port;
    private volatile Channel channel;

    public NettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public void start() {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //创建服务端启动对象 配置参数
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    // .handler(null)  //对应bossGroup
                    .handler(new ChannelInitializer<SocketChannel>() {  //对应workGroup 创建一个通道测试对象
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MsgDecoder())
                                    .addLast(new MsgEncoder())
                                    .addLast(new ClientHandler());
                        }
                    });
            //绑定一个端口并且同步
            //启动服务器
            ChannelFuture cf = bootstrap.connect(ip, port).sync();
            channel = cf.channel();
            channel.closeFuture().addListeners((ChannelFutureListener) future -> {
                workGroup.shutdownGracefully();
            });
            //对关闭通道进行监听
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel(){
        return channel;
    }
}

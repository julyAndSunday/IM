package com.github.chat.server.handler;

import com.github.chat.conn.ConnectManager;
import com.github.chat.service.UserService;
import com.github.common.messgae.ChatMessage;
import com.github.common.messgae.MessageType;
import com.github.chat.handler.MessageHandler;
import com.github.common.utils.JwtTokenUtil;
import com.github.common.utils.ServiceUtil;
import com.github.common.utils.SnowFlake;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description:
 * @Author: July
 * @Date: 2021-02-19 21:38
 **/
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<ChatMessage> {
    private String serviceName;
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    public ServerHandler(String serviceName) {
        this.serviceName = serviceName;
        jwtTokenUtil = new JwtTokenUtil();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connect");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) throws Exception {
        logger.info("read:"+msg);
        //设置全局唯一id
        if (msg.getId() == null) {
            msg.setId(snowFlake.nextId());
        }
        String messageType = msg.getMessageType();
        switch (messageType) {
            case MessageType.INIT:  //初始化连接
                //service和用户id 一一对应
                String token = msg.getContent();
                String id = jwtTokenUtil.getUserIdFromToken(token);
                if (id == null) {
                    ctx.channel().close();
                } else {
                    ctx.channel().attr(ConnectManager.NET_ID).set(id);
                    ServiceUtil.addIdInService(serviceName, id);
                    //用户id和连接 一一对应
                    ConnectManager.putId_channel(Integer.parseInt(id), ctx.channel());
                    logger.info("user:{} register in {}",id,serviceName);
                    //拉取离线消息
                }
                break;
            case MessageType.CHAT:  //私聊
                messageHandler.chat(msg,msg.getDestId());
                break;
            case MessageType.GROUP:  //群聊
                messageHandler.broadcast(msg);
                break;
            case MessageType.ACK:  //消息确认
                messageHandler.ack(msg);
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
       clearChannel(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        clearChannel(ctx);
    }


    private void clearChannel(ChannelHandlerContext ctx){
        String id = ctx.channel().attr(ConnectManager.NET_ID).get();
        //更新用户最后登录时间
        userService.updateLastLoginTimeByUserId(Integer.parseInt(id));
        //清除连接
        ConnectManager.removeChannelById(Integer.parseInt(id));
        ServiceUtil.deleteIdInService(id);
    }
}

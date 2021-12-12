package com.github.chat.server.handler;

import com.github.common.messgae.ChatMessage;
import com.github.common.messgae.MessageType;
import com.github.chat.handler.MessageHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-02-23 23:00
 **/
@ChannelHandler.Sharable
public class InternalHandler extends SimpleChannelInboundHandler<ChatMessage> {
    @Autowired
    private MessageHandler messageHandler;
    private Logger logger = LoggerFactory.getLogger(InternalHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) throws Exception {
//        System.out.println("internal");
//        logger.info("internal read:{}",msg);
//        String messageType = msg.getMessageType();
//        switch (messageType) {
//            case MessageType.CHAT:
//                messageHandler.chat(msg,msg.getDestId());
//                break;
//            case MessageType.GROUP:
//                break;
//        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常--" + cause.getMessage());
    }
}

package com.github.client.chat.handler;

import com.github.common.messgae.ChatMessage;
import com.github.common.messgae.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sun.misc.Unsafe;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 17:28
 **/
public class ClientHandler extends SimpleChannelInboundHandler<ChatMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) throws Exception {
        switch (msg.getMessageType()) {
            case MessageType.CHAT:
                System.out.println("from user-" + msg.getFromId() + " send:" + msg.getContent());
                sendAck(ctx.channel(),msg.getId());
                break;
            case MessageType.GROUP:
                System.out.println("from group-" + msg.getGroupId() + ":user-"+msg.getDestId()+" send:" + msg.getContent());
                sendAck(ctx.channel(),msg.getId());
                break;
        }
    }

   private void sendAck(Channel channel,long msgId){
       ChatMessage ack = new ChatMessage();
       ack.setId(msgId);
       ack.setMessageType(MessageType.ACK);
       channel.writeAndFlush(ack);
   }
}

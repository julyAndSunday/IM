package com.github.common.codec;

import com.github.common.messgae.ChatMessage;
import com.github.common.utils.ProtoUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-02-18 21:13
 **/
public class MsgEncoder extends MessageToByteEncoder<ChatMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ChatMessage message, ByteBuf byteBuf) throws Exception {
        byte[] bytes = ProtoUtils.serialize(message);
        int length = bytes.length;
        byteBuf.writeShort(length);
        byteBuf.writeBytes(bytes);
    }
}

package com.github.common.codec;

import com.github.common.messgae.ChatMessage;
import com.github.common.utils.ProtoUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-02-18 21:12
 **/
public class MsgDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(MsgDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        in.markReaderIndex();
        if (in.readableBytes() < 2) {
            in.resetReaderIndex();
            return;
        }
        short length = in.readShort();
        if (length < 0) {
            ctx.close();
            logger.error("[IM msg decoder]message length less than 0, channel closed");
            return;
        }
        if (length > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }
        ByteBuf buf = Unpooled.buffer(length);
        in.readBytes(buf);
        byte[] bytes = buf.array();

        ChatMessage chatMessage = ProtoUtils.deserialize(bytes, ChatMessage.class);
        list.add(chatMessage);
    }
}

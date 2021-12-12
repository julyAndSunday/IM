package com.github.chat.service;

import com.github.chat.mapper.ChatMessageMapper;
import com.github.common.messgae.ChatMessage;
import com.github.common.messgae.MessageType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-12-09 10:31
 **/
@Service
public class OfflineMsgService {
    @Resource
    private ChatMessageMapper chatMessageMapper;

    public void save(ChatMessage chatMessage){
        switch (chatMessage.getMessageType()){
            //私聊 直接存储
            case MessageType.CHAT:
                chatMessageMapper.insert(chatMessage);
                break;
            case MessageType.GROUP:

        }
    }
}

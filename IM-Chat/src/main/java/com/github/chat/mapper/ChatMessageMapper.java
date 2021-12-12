package com.github.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.chat.entity.Chatmessage;
import com.github.common.messgae.ChatMessage;

public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
    int deleteByPrimaryKey(Integer id);

    int insert(Chatmessage record);

    int insertSelective(Chatmessage record);

    Chatmessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Chatmessage record);

    int updateByPrimaryKey(Chatmessage record);
}
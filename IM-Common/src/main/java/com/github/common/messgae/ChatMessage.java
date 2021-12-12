package com.github.common.messgae;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-03-28 10:15
 **/
@Data
public class ChatMessage {
    private Long id;
    //消息类型
    private String messageType;
    private int groupId;
    private int destId;
    private int fromId;
    //内容类型 文本 文件
    private String contentType;
    private String content;
    private Date date;

    public ChatMessage() {
        date = new Date();
    }
}

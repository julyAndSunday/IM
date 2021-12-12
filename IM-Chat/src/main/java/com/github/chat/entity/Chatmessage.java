package com.github.chat.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ChatMessage
 * @author 
 */
@Data
public class Chatmessage implements Serializable {
    private Integer id;

    private Integer fromId;

    private Integer destId;

    private String content;

    private Date date;

    private static final long serialVersionUID = 1L;
}
package com.github.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * offline_msg
 * @author 
 */
@Data
public class OfflineMsg {
    private Integer id;

    private Integer fromId;

    private Integer toId;

    private String msg;

    private Date date;

}
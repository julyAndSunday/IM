package com.github.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * moments
 * @author 
 */
@Data
public class Moments  {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String imgs;

    /**
     * 点赞
     */
    private Integer likes;

    /**
     * 发布时间
     */
    private Date date;

    private static final long serialVersionUID = 1L;
}
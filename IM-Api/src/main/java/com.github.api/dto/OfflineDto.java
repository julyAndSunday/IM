package com.github.api.dto;

import com.github.common.entity.OfflineMsg;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-09-02 15:48
 **/
@Data
public class OfflineDto {
    private int type;  //0为私聊  1为群聊
    private List<? extends OfflineMsg> msgs;

    public OfflineDto(int type, List<? extends OfflineMsg> msgs) {
        this.type = type;
        this.msgs = msgs;
    }
}

package com.github.api.service;

import com.github.api.mapper.OfflineGroupMapper;
import com.github.api.mapper.OfflineMsgMapper;
import com.github.common.entity.OfflineGroup;
import com.github.common.entity.OfflineMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-09-01 16:19
 **/
@Service
public class OfflineMsgService {
    @Resource
    private OfflineMsgMapper offlineMsgMapper;
    @Resource
    private OfflineGroupMapper offlineGroupMapper;

    public void saveOfflineMsg(OfflineMsg offlineMsg) {
        offlineMsgMapper.insert(offlineMsg);
    }

    public void saveOfflineGroup(OfflineGroup offlineGroup) {
        offlineGroupMapper.insert(offlineGroup);
    }

    public List<OfflineMsg> getOfflineMsg(int userId) {
        return offlineMsgMapper.selectMsgsByUserId(userId);
    }

    public void updateGroupMsgService(int userId,int lastMsgId) {
        offlineGroupMapper.updateMsgsByUserId(userId,lastMsgId);
    }

    public void deleteMsgService(int userId,int lastMsgId) {
        offlineMsgMapper.deleteMsgsByUserId(userId,lastMsgId);
    }

    public List<OfflineGroup> getOfflineGroup(int userId,Date lastLoginTime) {
        return offlineGroupMapper.selectGroupByUserId(userId,lastLoginTime);
    }

    public void updateOfflineGroup(OfflineGroup offlineGroup) {
        offlineGroupMapper.updateById(offlineGroup);
    }
}

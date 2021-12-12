package com.github.chat.status;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-03-13 16:07
 **/
public class MemoryUserStatusService implements UserStatusService {
    private ConcurrentMap<Long, String> userIdConnectorIdMap;

    public MemoryUserStatusService() {
        this.userIdConnectorIdMap = new ConcurrentHashMap<>();
    }

    @Override
    public String online(long userId, String connectorId) {
        return userIdConnectorIdMap.put(userId, connectorId);
    }

    @Override
    public void offline(long userId) {
        userIdConnectorIdMap.remove(userId);
    }

    @Override
    public String getConnectorId(long userId) {
        return userIdConnectorIdMap.get(userId);
    }
}

package com.github.chat.status;

public interface UserStatusService {
    /**
     * user online
     *
     * @param userId
     * @param connectorId
     * @return the user's previous connection id, if don't exist then return null
     */
    String online(long userId, String connectorId);

    /**
     * user offline
     *
     * @param userId
     */
    void offline(long userId);

    /**
     * get connector id by user id
     *
     * @param userId
     * @return
     */
    String getConnectorId(long userId);
}

package com.github.chat.status;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-03-13 09:42
 **/
public class RedisUserStatusService implements UserStatusService{
    private static final Logger logger = LoggerFactory.getLogger(RedisUserStatusService.class);
    private static final String USER_CONN_KEY = "im:user_conn:USERID:";

    private JedisPool jedisPool;

    public RedisUserStatusService() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(2 * 1000);
        jedisPool = new JedisPool(config,"localhost", 6379, 2 * 1000,null);
    }


    @Override
    public String online(long userId, String connectorId) {
        logger.debug("[user status] user online: userId: {}, connectorId: {}", userId, connectorId);

        try (Jedis jedis = jedisPool.getResource()) {
            String oldConnectorId = jedis.hget(USER_CONN_KEY, String.valueOf(userId));
            jedis.hset(USER_CONN_KEY, String.valueOf(userId), connectorId);
            return oldConnectorId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void offline(long userId) {
        logger.debug("[user status] user offline: userId: {}", userId);

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(USER_CONN_KEY, String.valueOf(userId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String getConnectorId(long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(USER_CONN_KEY, String.valueOf(userId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

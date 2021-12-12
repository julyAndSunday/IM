package com.github.common.utils.redis;

import com.github.common.utils.zookeeper.ZkClient;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-19 22:12
 **/
public class RedisClient {
    private ShardedJedisPool pool;

    public RedisClient(String host, int port, String password) {
        // 配置Redis信息
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        // 集群
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(host, port);
        // 设置Redis的密码
        jedisShardInfo1.setPassword(password);
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        pool = new ShardedJedisPool(config, list);
    }

    public String add(String key, String value) {
        return pool.getResource().set(key, value);
    }

    public String get(String key) {
        return pool.getResource().get(key);
    }

    public void lpush(String pre, String key, String value) {
        pool.getResource().lpush(pre, key, value);
    }

    public List<String> lrange(String key) {
        return pool.getResource().lrange(key, 0, -1);
    }

    public Long sadd(String key, String value) {
        return pool.getResource().sadd(key, value);
    }

    public long srem(String key, String value) {
        return pool.getResource().srem(key, value);
    }

    public boolean sismember(String key, String value) {
        return pool.getResource().sismember(key, value);
    }

    public void hset(String key, String filed, String value) {
        pool.getResource().hset(key, filed, value);
    }

    public static void main(String[] args) {
        RedisClient redisClient = new RedisClient("120.79.220.182", 6379, "abc987");
        redisClient.pool.getResource()
                .sadd("service2","10");
    }
}

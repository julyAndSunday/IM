package com.github.chat.conn;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-20 11:46
 **/
public class ConnectManager {
    //用户连接
    private static ConcurrentHashMap<Integer, Channel> id_channelMap = new ConcurrentHashMap<>();
    //内部服务连接
    private static ConcurrentHashMap<String, Channel> service_channelMap = new ConcurrentHashMap<>();
    //chanel连接的id 即userId
    public static AttributeKey<String> NET_ID = AttributeKey.valueOf("netId");


    public ConnectManager() {
        id_channelMap = new ConcurrentHashMap<>();
        service_channelMap = new ConcurrentHashMap<>();
    }

    public static void putId_channel(int id, Channel channel) {
        id_channelMap.put(id, channel);
    }

    public static boolean containUserId(int userId) {
        return id_channelMap.containsKey(userId);
    }

    public static Channel getByUserId(int userId) {
        return id_channelMap.get(userId);
    }

    public static void removeChannelById(int id) {
        id_channelMap.remove(id);
    }


    public static void putService_channel(String serviceName, Channel channel) {
        service_channelMap.put(serviceName, channel);
    }

    public static boolean containService(String serviceName) {
        return service_channelMap.containsKey(serviceName);
    }

    public static Channel getByService(String serviceName) {
        return service_channelMap.get(serviceName);
    }

}




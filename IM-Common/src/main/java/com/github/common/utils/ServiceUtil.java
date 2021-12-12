package com.github.common.utils;


import com.github.common.entity.Peer;
import com.github.common.entity.RegisterService;
import com.github.common.utils.redis.RedisClient;
import com.github.common.utils.zookeeper.ZkClient;
import com.github.common.utils.zookeeper.ZkConstant;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-19 22:56
 **/
public class ServiceUtil {
    private static RedisClient redisClient;
    private static ZkClient zkClient;

    static {
        zkClient = new ZkClient("120.79.220.182:2181");
        redisClient = new RedisClient("120.79.220.182", 6379, "abc987");
    }

    public static void addIdInService(String serviceName, String id) {
        redisClient.sadd(serviceName, id);
    }

    public static void deleteIdInService(String id) {
        List<String> serviceNames = getAllServiceName();
        if (serviceNames != null) {
            for (String name : serviceNames) {
                redisClient.srem(name, id);
            }
        }
    }

    public static void registerService(String serviceName, Peer peer) {
        try {
            String address = peer.getIp() + ":" + peer.getPort();
            zkClient.createPathData("/"+serviceName, address.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Peer getServiceByName(String serviceName) {
        Peer peer = null;
        try {
            byte[] data = zkClient.getData("/" + serviceName);
            String[] address = new String(data).split(":");
            peer = new Peer(address[0], Integer.parseInt(address[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return peer;
    }

    public static List<RegisterService> getAllService() {
        List<RegisterService> registerServices = new ArrayList<>();
        try {
            List<String> serviceNames = getAllServiceName();
            if (serviceNames != null) {
                for (String name : serviceNames) {
                    RegisterService registerService = new RegisterService();
                    byte[] data = zkClient.getData("/" + name);
                    registerService.setName(name);
                    String[] address = new String(data).split(":");
                    registerService.setPeer(new Peer(address[0], Integer.parseInt(address[1])));
                    registerServices.add(registerService);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerServices;
    }

    public static List<String> getAllServiceName() {
        try {
            return zkClient.getChildren(ZkConstant.ZK_REGISTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAllAddress() {
        List<byte[]> allRegister = null;
        List<String> address = new ArrayList<>();
        try {
            allRegister = zkClient.getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (allRegister != null) {
            for (byte[] bytes : allRegister) {
                address.add(new String(bytes));
            }
        }
        return address;
    }

    public static List<String> getServiceById(String id) {
        List<String> serviceList = new ArrayList<>();
        List<String> serviceNames = getAllServiceName();
        for (String one : serviceNames) {
            if (redisClient.sismember(one, id)) {
                serviceList.add(one);
            }
        }
        return serviceList;
    }

    public static void addZkListener(CuratorListener curatorListener){
        zkClient.addStateListener(curatorListener);
    }

    public static void watchPathChildrenNode(String path, PathChildrenCacheListener listener)  {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient.getClient(), path, true);
        //BUILD_INITIAL_CACHE 代表使用同步的方式进行缓存初始化。
        try {
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pathChildrenCache.getListenable().addListener(listener);
    }

    public static void main(String[] args) {
        List<String> serviceById = getServiceById("18");
    }

}

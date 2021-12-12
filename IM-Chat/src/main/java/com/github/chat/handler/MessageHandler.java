package com.github.chat.handler;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.chat.conn.ConnectManager;
import com.github.chat.service.GroupService;
import com.github.chat.service.OfflineMsgService;
import com.github.common.messgae.ChatMessage;
import com.github.chat.server.InternalService;
import com.github.common.utils.ServiceUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-20 15:18
 **/
@Component
public class MessageHandler {
    @Autowired
    private GroupService groupService;
    @Autowired
    private OfflineMsgService offlineMsgService;
    private Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    private ConcurrentHashMap<Integer, List<Integer>> groups = new ConcurrentHashMap<>();

    private ScheduledThreadPoolExecutor retransmissionExecutor = new ScheduledThreadPoolExecutor(3);
    private ConcurrentHashMap<Long, Integer> retransmissionMap = new ConcurrentHashMap<>();
    //消息超时时间
    private final int timeout = 2;
    //重传次数
    private final int retransmissionTime = 3;

    public void chat(ChatMessage msg, int destId) {
        if (ConnectManager.containUserId(destId)) {   //目标连接在当前service中
            Channel channel = ConnectManager.getByUserId(destId);
            if (channel.isActive()) {     //连接存活
                reliableSend(channel,msg);
            } else {   //连接已断开
                //存储离线消息
                offlineMsgService.save(msg);
                //关闭连接 移除对应关系
                channel.close();
                ServiceUtil.deleteIdInService(String.valueOf(destId));
                ConnectManager.removeChannelById(destId);
            }
        } else {   //不在该service中
            List<String> serviceNames = ServiceUtil.getServiceById(String.valueOf(destId));
            if (CollectionUtils.isEmpty(serviceNames)) { //所有service不存在该连接 进行离线存储
                offlineMsgService.save(msg);
            } else {
                for (String name : serviceNames) {
                    //rpc发送给其他service处理
                    msg.setDestId(destId);
                    InternalService.send(name, msg);
                }
            }
        }
    }

    public void broadcast(ChatMessage msg) {
        //群聊消息有标记给某个用户
        if (msg.getDestId() != 0) {
            Channel channel = ConnectManager.getByUserId(msg.getDestId());
            if (channel.isActive()) {
                reliableSend(channel,msg);
            } else {
                channel.close();
            }
            return;
        }
        //没有标记某个用户 进行广播
        int groupId = msg.getGroupId();
        List<Integer> userIds = groups.get(groupId);
        if (CollectionUtils.isEmpty(userIds)) {
            userIds = groupService.getUserIdsByGroupId(groupId);
            groups.put(groupId, userIds);
        }
        for (int userId : userIds) {
            if (userId != msg.getFromId()) {
                chat(msg, userId);
            }
        }
    }

    /**
     * 可靠消息传递
     * @param channel
     * @param chatMessage
     */
    private void reliableSend(Channel channel, ChatMessage chatMessage) {
        retransmissionMap.put(chatMessage.getId(), retransmissionTime);
        channel.writeAndFlush(chatMessage);
        //开启超时重传
        retransmissionExecutor.schedule(new Retransmission(channel, chatMessage), timeout, TimeUnit.SECONDS);
    }

    private class Retransmission implements Runnable {
        private Channel channel;
        private ChatMessage chatMessage;

        private Retransmission(Channel channel, ChatMessage chatMessage) {
            this.channel = channel;
            this.chatMessage = chatMessage;
        }

        @Override
        public void run() {
            long id = chatMessage.getId();
            if (retransmissionMap.containsKey(id)) {
                if (channel.isActive()) {
                    channel.writeAndFlush(chatMessage);
                    logger.info("retransmission msg:{}", id);
                    //id存在则将重传次数减一
                    retransmissionMap.computeIfPresent(id, (key, value) -> --value);
                    //继续重传或结束
                    if (retransmissionMap.containsKey(id) && retransmissionMap.get(id) > 0) {
                        retransmissionExecutor.schedule(new Retransmission(channel, chatMessage), timeout, TimeUnit.SECONDS);
                    } else {
                        retransmissionMap.remove(id);
                    }
                }
            }
        }
    }

    public void ack(ChatMessage msg) {
        retransmissionMap.remove(msg.getId());
    }

}

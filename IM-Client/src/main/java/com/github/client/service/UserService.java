package com.github.client.service;

import com.github.client.chat.NettyClient;
import com.github.client.rest.AbstractRestService;
import com.github.client.rest.api.UserRestService;
import com.github.client.store.UserInfo;
import com.github.common.entity.ChatGroup;
import com.github.common.entity.RegisterService;
import com.github.common.entity.User;
import com.github.common.entity.UserLoginResponse;
import com.github.common.messgae.ChatMessage;
import com.github.common.messgae.MessageType;
import com.github.common.utils.JwtTokenUtil;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 15:00
 **/
public class UserService extends AbstractRestService<UserRestService> {
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    public UserService(Class<UserRestService> clazz, String url) {
        super(clazz, url);
    }

    public UserInfo login(String username, String password) {
        UserLoginResponse response = doRequest(() ->
                restClient.login(new User(username, password)).execute());
        if (response!= null) {
            String token = response.getToken();
            UserInfo userInfo = new UserInfo();
            userInfo.setToken(token);
            String id = jwtTokenUtil.getUserIdFromToken(token);
            userInfo.setId(Integer.parseInt(id));
            String uname = jwtTokenUtil.getUserNameFromToken(token);
            userInfo.setUsername(uname);
            //获取服务ip端口
            RegisterService service = response.getRegisterService();
            //连接并启动
            NettyClient nettyClient = new NettyClient(service.getPeer().getIp(), service.getPeer().getPort());
            nettyClient.start();
            Channel channel = nettyClient.getChannel();
            //发送token进行检验并初始化连接
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(MessageType.INIT);
            chatMessage.setContent(response.getToken());
            try {
                channel.writeAndFlush(chatMessage).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userInfo.setChannel(channel);
            return userInfo;
        }
        return null;
    }

    public List<User> getFriends(int id) {
        return doRequest(() ->
                restClient.getFriendsByUserId(id).execute());
    }

    public List<ChatGroup> getGroups(int id) {
        return doRequest(() ->
                restClient.getGroupsByUserId(id).execute());
    }

}

package com.github.client.command;

import com.github.client.chat.NettyClient;
import com.github.client.exception.ImException;
import com.github.client.rest.api.UserRestService;
import com.github.client.service.UserService;
import com.github.client.store.UserInfo;
import com.github.common.entity.ChatGroup;
import com.github.common.entity.RegisterService;
import com.github.common.entity.User;
import com.github.common.entity.UserLoginResponse;
import com.github.common.messgae.ChatMessage;
import com.github.common.messgae.MessageType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 19:59
 **/
public class CommandHandler {

    static Map<String, CommandFunction> commandMap = new HashMap<>();
    private UserService userService = new UserService(UserRestService.class, "http://localhost:9000/");
    private UserInfo userInfo;
    public void  handle(String command, String... args) {
        if (!commandMap.containsKey(command)){
            System.out.println("not command");
            return;
        }
        commandMap.get(command).execute(args);
    }

    @FunctionalInterface
    protected interface CommandFunction {
        void execute(String... arg);
    }


    public CommandHandler() {
        commandMap.put("friends", args -> {
            List<User> friends = userService.getFriends(userInfo.getId());
            System.out.println("id  nickname");
            for (User friend : friends) {
                System.out.println(friend.getId() + "   " + friend.getNickname());
            }
        });

        commandMap.put("groups",args -> {
            List<ChatGroup> groups = userService.getGroups(userInfo.getId());
            System.out.println("id  groupName");
            for (ChatGroup group : groups) {
                System.out.println(group.getId() + "   " + group.getName());
            }
        });
        commandMap.put("login", args -> {
            if (args.length < 2) {
                System.out.println("args error");
                return;
            }
            userInfo = userService.login(args[0], args[1]);
            if (userInfo.getChannel()!=null && userInfo.getChannel().isActive()) {
                System.out.println("登录成功");
            } else {
                System.out.println("用户名或密码错误");
            }
        });
        commandMap.put("send",args -> {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(MessageType.CHAT);
            chatMessage.setFromId(userInfo.getId());
            chatMessage.setDestId(Integer.parseInt(args[0]));
            StringBuilder content = new StringBuilder();
            for (int i=1;i<args.length;i++){
                content.append(args[i]);
            }
            chatMessage.setContent(content.toString());
            userInfo.getChannel().writeAndFlush(chatMessage);
        });

        commandMap.put("broadcast",args -> {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(MessageType.GROUP);
            chatMessage.setFromId(userInfo.getId());
            chatMessage.setGroupId(Integer.parseInt(args[0]));
            StringBuilder content = new StringBuilder();
            for (int i=1;i<args.length;i++){
                content.append(args[i]);
            }
            chatMessage.setContent(content.toString());
            userInfo.getChannel().writeAndFlush(chatMessage);
        });
    }

}

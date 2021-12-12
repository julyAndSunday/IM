package com.github.api.controller;

import com.github.api.dto.UserLoginResponse;
import com.github.api.service.ChatGroupService;
import com.github.common.entity.ChatGroup;
import com.github.common.entity.Relation;
import com.github.api.service.UserService;
import com.github.common.common.Result;
import com.github.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-05-01 10:50
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatGroupService chatGroupService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        UserLoginResponse response = userService.login(user);
        if (response!=null) {
            return Result.success(response);
        }
        return Result.filed(null);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        int id = userService.register(user);
        return Result.success(id);
    }

    @GetMapping("/friends/{id}")
    public Result getFriendsByUserId(@PathVariable("id")int id){
        List<User> friends = userService.getFriends(id);
        return Result.success(friends);
    }

    @GetMapping("/groups/{id}")
    public Result getGroupsByUserId(@PathVariable("id")int id){
        List<ChatGroup> groups = chatGroupService.getGroupByUserId(id);
        return Result.success(groups);
    }

    @PostMapping("/addFriend")
    public Result addFriend(@RequestBody Relation relation){
        int id = userService.addFriend(relation);
        if (id>0){
            return Result.success(id);
        }else {
            return Result.filed(id);
        }
    }
}

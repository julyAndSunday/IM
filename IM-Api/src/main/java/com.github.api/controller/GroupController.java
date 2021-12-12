//package com.github.api.controller;
//
//import com.github.api.dto.CreateGroup;
//import com.github.common.entity.ChatGroup;
//import com.github.api.service.ChatGroupService;
//import com.github.api.service.UserGroupService;
//import com.github.common.common.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @Description:
// * @Author: July
// * @Date: 2021-08-27 10:30
// **/
//@RestController
//@RequestMapping("/group")
//public class GroupController {
//
//    @Autowired
//    private ChatGroupService chatGroupService;
//    @Autowired
//    private UserGroupService userGroupService;
//
//    @PostMapping("/create")
//    @Transactional
//    public Result createGroup(@RequestBody CreateGroup createGroup){
//        Integer groupId = chatGroupService.createGroup(new ChatGroup(createGroup.getName(), createGroup.getUserId()));
//        List<Integer> friendIds = createGroup.getFriendId();
//        friendIds.add(createGroup.getUserId());
//        userGroupService.joinGroup(String.valueOf(groupId), friendIds);
//        return Result.success(groupId);
//    }
//
//    @GetMapping("/get/{id}")
//    public Result getGroup(@PathVariable("id")int userId){
//        List<ChatGroup> groups = chatGroupService.getGroupByUserId(userId);
//        return Result.success(groups);
//    }
//}

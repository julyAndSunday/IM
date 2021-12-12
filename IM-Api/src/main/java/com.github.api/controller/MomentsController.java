//package com.github.api.controller;
//
//import com.baomidou.mybatisplus.extension.api.R;
//import com.github.common.entity.Moments;
//import com.github.api.service.MomentsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @Description:
// * @Author: July
// * @Date: 2021-09-17 22:50
// **/
//@RestController("/moment")
//public class MomentsController {
//    @Autowired
//    public MomentsService momentsService;
//
//    @PostMapping("/post")
//    public R postMoment(@RequestBody Moments moments){
//        int res = momentsService.postMoment(moments);
//        return R.ok(res);
//    }
//
//    /**
//     * 获取一个人朋友圈
//     * @param userId 用户id
//     * @return
//     */
//    @GetMapping("/getOne/{id}")
//    public R getMomentsByUserId(@PathVariable("id")int userId){
//        List<Moments> moments = momentsService.getMomentsByUserId(userId);
//        return R.ok(moments);
//    }
//
//
//    /**
//     * 获取好友朋友圈
//     * @param userId 用户id
//     * @return
//     */
//    @GetMapping("getAll/{id}")
//    public R getFriendsMoments(@PathVariable("id")int userId){
//        List<Moments> moments = momentsService.getFriendsMoments(userId);
//        return R.ok(moments);
//    }
//}

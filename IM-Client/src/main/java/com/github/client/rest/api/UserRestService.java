package com.github.client.rest.api;

import com.github.common.common.Result;
import com.github.common.entity.ChatGroup;
import com.github.common.entity.Relation;
import com.github.common.entity.User;
import com.github.common.entity.UserLoginResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 15:00
 **/
public interface UserRestService {

    @Headers("Content-Type: application/json")
    @POST("/user/login")
    Call<Result<UserLoginResponse>> login(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("/user/register")
    Call<Result<Integer>> register(@Body User user);

    @Headers("Content-Type: application/json")
    @GET("/user/friends/{id}")
    Call<Result<List<User>>> getFriendsByUserId(@Path("id")int id);

    @Headers("Content-Type: application/json")
    @GET("/user/groups/{id}")
    Call<Result<List<ChatGroup>>> getGroupsByUserId(@Path("id")int id);

    @Headers("Content-Type: application/json")
    @POST("/user/addFriends")
    Call<Result<Integer>> addFriend(@Body Relation relation);
}

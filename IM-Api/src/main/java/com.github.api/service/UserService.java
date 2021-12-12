package com.github.api.service;

import com.github.api.dto.UserLoginResponse;
import com.github.common.entity.Relation;
import com.github.api.mapper.RelationMapper;
import com.github.api.mapper.UserMapper;
import com.github.api.route.ChatServiceRouter;
import com.github.common.entity.RegisterService;
import com.github.common.entity.User;
import com.github.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private ChatServiceRouter router;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RelationMapper relationMapper;
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    public UserLoginResponse login(User user) {
        Integer id = userMapper.selectIdByUsernameAndPassword(user.getUsername(), user.getPassword());
        UserLoginResponse response = null;
        if (id != null && id > 0) {
            response = new UserLoginResponse();
            user.setId(id);
            String token = jwtTokenUtil.generateToken(user);
            response.setToken(token);
            RegisterService service = router.route(id);
//            RegisterService service = new RegisterService();
            response.setRegisterService(service);
        }
        return response;
    }

    public int register(User user) {
        Integer id = userMapper.selectIdByUsername(user.getUsername());
        if (id == null) {
            userMapper.insert(user);
        }
        return user.getId();
    }
    public int addFriend(Relation relation) {
        int id = relationMapper.selectRelation(relation.getUserId(),relation.getFriendId());
        if (id > 0) {
            return id;
        }
        return relationMapper.insert(relation);
    }

    public List<User> getFriends(int id) {
        return userMapper.selectFriends(id);
    }
}

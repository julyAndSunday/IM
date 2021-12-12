package com.github.api.service;

import com.github.api.mapper.MomentsMapper;
import com.github.common.entity.Moments;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-09-17 22:51
 **/
@Service
public class MomentsService {
    @Resource
    private MomentsMapper momentsMapper;

    public int postMoment(Moments moments) {
        return momentsMapper.insert(moments);
    }

    public List<Moments> getMomentsByUserId(int userId) {
        return momentsMapper.selectByUserId(userId);
    }

    public List<Moments> getFriendsMoments(int userId) {
        return momentsMapper.selectFriendsMoments(userId);
    }
}

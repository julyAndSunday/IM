package com.github.api.route.impl;

import com.github.api.route.ChatServiceRouter;
import com.github.common.entity.RegisterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-23 11:12
 **/
@Service
public class ModRouter extends ChatServiceRouter {

    @Override
    public RegisterService route(Integer id) {
        List<RegisterService> allService = getAllService();
        int index = id % allService.size();
        return allService.get(index);
    }
}

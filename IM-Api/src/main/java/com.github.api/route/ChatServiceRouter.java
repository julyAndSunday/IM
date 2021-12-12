package com.github.api.route;

import com.github.common.entity.RegisterService;
import com.github.common.utils.ServiceUtil;

import java.util.List;

public abstract class ChatServiceRouter {

   public abstract RegisterService route(Integer id);

   protected List<RegisterService> getAllService(){
       return ServiceUtil.getAllService();
    }
}

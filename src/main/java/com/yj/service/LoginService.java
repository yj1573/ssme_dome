package com.yj.service;

import com.yj.config.RModel;
import com.yj.controller.UserController;
import com.yj.mapper.UserMapper;
import com.yj.model.User;
import com.yj.session.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper um;
    //缓存穿透问题
    @Cacheable(value = "userCache",key = "#username",unless = "#result==null")
    public User loginUser(String username, String passwd){
        HashMap<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("passwd",passwd);
        User user=um.userLogin(map);
        log.info("从数据库查询到的数据{}",user);
        return user;
    }

    public RModel userLogin(String username, String passwd){
        User user=loginUser(username,passwd);
        if(user==null){
            return RModel.err();
        }
        user.setToken(SessionUtil.createToken());
        return RModel.ok().setData(user);
    }



}

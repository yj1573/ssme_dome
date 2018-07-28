package com.yj.service;

import com.yj.config.RModel;
import com.yj.mapper.UserMapper;
import com.yj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper um;

    @Transactional
    public RModel addUser(User user){
        Integer id=um.saveUser(user);
        //throw new RuntimeException();
        return RModel.ok().setData(id);
    }





}

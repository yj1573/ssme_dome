package com.yj.mapper;

import com.yj.model.User;

import java.util.Map;

public interface UserMapper {
    Integer saveUser(User user);
    User userLogin(Map<String,String> map);
}

package com.yj.controller;

import com.yj.config.RModel;
import com.yj.model.User;
import com.yj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api("api-用户相关接口")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService us;

    @ApiOperation(value = "添加用户",notes = "用户详细实体信息",response = RModel.class)
    @PostMapping("/add")
    public RModel addUser(User user){//@RequestBody
        return us.addUser(user);
    }



}

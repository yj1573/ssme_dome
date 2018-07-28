package com.yj.controller;

import com.yj.config.RModel;
import com.yj.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("api-用户登录接口")
@RestController
@RequestMapping("/api/login")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService ls;

    @ApiOperation(value = "用户登录",notes = "用户登录",response = RModel.class)
    @PostMapping("/userLogin")
    public RModel userLogin(@ApiParam("用户名")String username,@ApiParam("密码") String passwd){
        log.info("用户名：{} 密码：{}",username,passwd);
        return ls.userLogin(username,passwd);
    }

}

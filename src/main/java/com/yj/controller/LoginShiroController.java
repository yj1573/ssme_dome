package com.yj.controller;

import com.yj.config.RModel;
import com.yj.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("api-用户登录接口")
@RestController
@RequestMapping("/api/login")
public class LoginShiroController {
    private static final Logger log = LoggerFactory.getLogger(LoginShiroController.class);
    @Autowired
    private LoginService ls;

    @ApiOperation(value = "用户登录",notes = "用户登录",response = RModel.class)
    @PostMapping("/shiroLogin")
    public RModel shiroLogin(@ApiParam("用户名")String username,@ApiParam("密码") String passwd){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,passwd);
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return ls.userLogin(username,passwd);
    }

}

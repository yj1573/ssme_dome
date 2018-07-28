package com.yj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//启用WebMvc自定义配置
@EnableWebMvc
//开启事务注解
@EnableTransactionManagement
//配置mapper接口
@MapperScan("com.yj.mapper")
//启用缓存
@EnableCaching
public class SSMEApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SSMEApplication.class, args);
    }

    //打包成war第四步继承SpringBootServletInitializer实现configure方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SSMEApplication.class);
    }



}

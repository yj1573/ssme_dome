package com.yj.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mybatis的分页插件pageHelper
 */
@Configuration
public class MybatisPageHelper {

    //第一步 引入pageHelper jar包
    //第二步配置PageHelper bean
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    //第三步 执行查询写法
    /*
    PageHelper.startPage(当前页,条数);
    list=要执行的查询方法
    PageInfo<?> pageInfo = new PageInfo<>(list);
    */




}

package com.yj.controller;


import com.yj.cache.Ehcache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("api-Ehcache测试")
@RestController
@RequestMapping("/api/ehcache")
public class EhcacheController {
    private static final Logger log = LoggerFactory.getLogger(EhcacheController.class);
    @Autowired
    private Ehcache ehcache;

    @ApiOperation(value = "ehcache测试",notes = "ehcache测试",response = String.class)
    @GetMapping("/testCache")
    public String test(){
        Object id=ehcache.get("11");
        log.info("Controller获取的数据{}",id);
        return id.toString();
    }
}

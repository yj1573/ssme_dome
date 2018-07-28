package com.yj.config;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BloomFilterLnterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(BloomFilterLnterceptor.class);

    //创建布隆过滤器(默认3%误差)
    static BloomFilter bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 1000);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果布隆过滤器中不存在这个用户直接返回，将流量挡掉
        if(!bf.mightContain("")){
            System.out.println("bloom filter don't has this user");
            return false;
        }
        return true;
    }
}

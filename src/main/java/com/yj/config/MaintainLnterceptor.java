package com.yj.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//系统维护拦截器
public class MaintainLnterceptor extends HandlerInterceptorAdapter {
    public static volatile boolean LOCK=true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return LOCK;
    }


}

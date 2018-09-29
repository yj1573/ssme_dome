package com.yj.config;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 限流
 */
public class RateLimitLnterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(RateLimitLnterceptor.class);
    private static final RateLimiter rateLimiter = RateLimiter.create(200);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path=request.getRequestURI().toString();
        log.info("请求路径 {}",path);
        HandlerMethod hm= (HandlerMethod) handler;
        log.info("请求方法 {}",hm.getMethod().getName());
        if (!rateLimiter.tryAcquire()) {
            log.info("当前服务器繁忙，请稍后再试");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}

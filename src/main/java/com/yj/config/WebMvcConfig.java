package com.yj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * mvc配置
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {

    //配置Cors跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
        .allowedOrigins("*")
        .allowedMethods("GET","POST")//必须是大写
        .allowedHeaders("");//.allowCredentials(true).maxAge(3600)
    }

    //静态资源配置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //swagger2访问配置,不使用@EnableWebMvc注解不需要配置
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**")
                .addResourceLocations("/");
        //swagger2访问配置
        //静态资源访问配置
        registry.addResourceHandler("/view/**")
                .addResourceLocations("classpath:/public/").addResourceLocations("classpath:/static/");
        //静态资源访问配置
    }

    //拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimiter()).addPathPatterns("/api/**");
        registry.addInterceptor(login()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptorAdapter rateLimiter(){
        RateLimitLnterceptor rateLimiter=new RateLimitLnterceptor();
        return rateLimiter;
    }

    @Bean
    public HandlerInterceptorAdapter login(){
        LoginLnterceptor login=new LoginLnterceptor();
        return login;
    }

    @Bean
    public HandlerInterceptorAdapter maintain(){
        MaintainLnterceptor maintain=new MaintainLnterceptor();
        return maintain;
    }



}

package com.yj.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 执行时间粗略统计
 */

@Aspect
@Configuration
public class InvokingTime {
    private static final Logger log = LoggerFactory.getLogger(InvokingTime.class);

    @Pointcut("execution(* com.yj.controller..*.*(..))")
    public void pointCut(){}

    @Around(value="pointCut()")
    public Object invoking(ProceedingJoinPoint pjp) throws Throwable{
        /**
         * 获取目标方法相关信息
         */
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        //目标方法的完整名字
        StringBuilder mehodName = new StringBuilder();
        //得到目标方法所属类的名字
        String className = targetMethod.getDeclaringClass().getName();
        mehodName.append(className+".");
        //得到目标方法的名字
        String targetMethodName = targetMethod.getName();
        mehodName.append(targetMethodName);
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long executeTime = System.currentTimeMillis()- start;
        log.info("方法名：{} 耗时：{} ms",mehodName,executeTime);
        return result;
    }

}

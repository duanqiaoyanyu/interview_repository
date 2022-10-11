package com.cskaoyan.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/11 19:00
 */
@Slf4j
@Aspect
@Component
public class KaoYanAspect {

    @Pointcut(value = "execution(* com.cskaoyan.service.BusinessService.open())")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void prepareFood() {
        log.info("[前置通知] 方法执行前...");
    }

    @After("pointcut()")
    public void afterMethod() {
        log.info("[后置通知] 方法执行后...");
    }

    @AfterReturning("pointcut()")
    public void afterReturn() {
        log.info("[返回通知] 方法执行后...");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("[异常通知] 抛出异常后...");
    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("[环绕通知] 目标方法执行前...");
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        log.info("[环绕通知] 目标方法执行后");
        return proceed;
    }
}

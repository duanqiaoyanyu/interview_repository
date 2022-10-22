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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 研究生切面
 *
 * @author duanqiaoyanyu
 * @date 2022/10/18 14:58
 */
@Slf4j
@Aspect
@Order(value = 2)
@Component
public class GraduateAspect {

    @Pointcut(value = "execution(* com.cskaoyan.service.impl.ComplexServiceImpl.study())")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[环绕前逻辑-研究生切面]");
        Object proceed = proceedingJoinPoint.proceed();
        log.info("[环绕后逻辑-研究生切面]");

        return proceed;
    }

    @Before("pointcut()")
    public void before() {
        log.info("[前置通知-研究生切面]");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("[返回通知-研究生切面]");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("[异常通知-研究生切面]");
    }

    @After("pointcut()")
    public void after() {
        log.info("[后置通知-研究生切面]");
    }
}

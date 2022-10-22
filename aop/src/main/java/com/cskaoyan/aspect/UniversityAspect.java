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
 * @author duanqiaoyanyu
 * @date 2022/10/12 10:54
 */
@Slf4j
@Order(value = 1)
@Aspect
@Component
public class UniversityAspect {

    /**
     * 写法一(通过 @annotation(注解全包名))
     */
//    @Before(value = "@annotation(com.cskaoyan.annotation.University)")
//    public void before() {
//        Method[] methods = ReflectUtil.getMethods(UniversityServiceImpl.class, m -> {
//            University annotation = m.getAnnotation(University.class);
//            System.out.println(annotation);
//            return Objects.nonNull(annotation);
//        });
//
//        Method method = methods[0];
//        Map<String, Object> map = AnnotationUtil.getAnnotationValueMap(method, University.class);
//        System.out.println(map);
//        log.info("[前置通知] 大学切面");
//    }

    /**
     * 方式二(在形参中引入变量, 这样切点就不需要写全包名了, 直接写注解名)
     */
//    @Before(value = "@annotation(university)")
//    public void before(University university) {
//        Method[] methods = ReflectUtil.getMethods(UniversityServiceImpl.class, m -> {
//            University annotation = m.getAnnotation(University.class);
//            System.out.println(annotation);
//            return Objects.nonNull(annotation);
//        });
//
//        Method method = methods[0];
//        Map<String, Object> map = AnnotationUtil.getAnnotationValueMap(method, University.class);
//        System.out.println(map);
//        log.info("[前置通知] 大学切面");
//    }

//    @Around("pointcut()")
//    public Object around() {
//        log.info("");
//    }

    @Pointcut(value = "execution(* com.cskaoyan.service.impl.ComplexServiceImpl.study())")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[环绕前逻辑-大学切面]");
        Object proceed = proceedingJoinPoint.proceed();
        log.info("[环绕后逻辑-大学切面]");

        return proceed;
    }

    @Before("pointcut()")
    public void before() {
        log.info("[前置通知-大学切面]");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("[返回通知-大学切面]");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("[异常通知-大学切面]");
    }

    @After("pointcut()")
    public void after() {
        log.info("[后置通知-大学切面]");
    }
}

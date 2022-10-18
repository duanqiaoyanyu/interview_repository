package com.cskaoyan.aspect;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import com.cskaoyan.annotation.University;
import com.cskaoyan.service.UniversityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/12 10:54
 */
@Slf4j
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
    @Before(value = "@annotation(university)")
    public void before(University university) {
        Method[] methods = ReflectUtil.getMethods(UniversityServiceImpl.class, m -> {
            University annotation = m.getAnnotation(University.class);
            System.out.println(annotation);
            return Objects.nonNull(annotation);
        });

        Method method = methods[0];
        Map<String, Object> map = AnnotationUtil.getAnnotationValueMap(method, University.class);
        System.out.println(map);
        log.info("[前置通知] 大学切面");
    }


}

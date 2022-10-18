package com.cskaoyan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/12 10:46
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface University {

    boolean isC9() default false;

    boolean is985() default false;

    boolean is211() default false;

    String category() default "technology";

    String province();

    String city();
}

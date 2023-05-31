package com.cskaoyan.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/24 19:55
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (StrUtil.equals("kaoYanComponent", beanName)) {
            System.out.println("后置处理器 前置处理");
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StrUtil.equals("kaoYanComponent", beanName)) {
            System.out.println("后置处理器 后置处理");
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}

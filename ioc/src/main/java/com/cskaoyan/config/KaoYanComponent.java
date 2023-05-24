package com.cskaoyan.config;

import lombok.Data;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/24 18:57
 */
@Data
@Component
public class KaoYanComponent implements InitializingBean, BeanNameAware {

    private String name = "默认名字";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = "你的名字";
        System.out.println("设置名字");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String content;
    private String value;


    // ====
    public KaoYanComponent() {
        System.out.println("对象实例化执行...");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware 方法执行...");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct...执行");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("可用性检查");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("preDestroy... 执行");
    }
}

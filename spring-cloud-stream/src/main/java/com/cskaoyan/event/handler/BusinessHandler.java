package com.cskaoyan.event.handler;

import com.cskaoyan.event.model.BusinessModel;
import com.cskaoyan.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * 某某业务消息处理器
 *
 */
@Slf4j
@Configuration
public class BusinessHandler {

    @Autowired
    private BusinessService businessService;

    /**
     * 注意这里的 beanName 要与配置文件中的函数声明一致, 如果不手动给 bean 命名, 取的则是方法名.
     *
     * @return
     */
    @Bean
    Consumer<BusinessModel> consumerName() {
        return model -> {
            log.info("收到 [某某业务消息]... 成功, model: {}", model);
            businessService.handle(model);
        };
    }
}

package com.cskaoyan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/4 19:06
 */
@Configuration
public class MybatisPlusConfiguration {

    /**
     * SQL 注入器
     *
     * @return
     */
    @Bean
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }
}

package com.cskaoyan.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/19 11:04
 */
@Configuration
public class RedissonConfiguration {

    @Value("${spring.redisson.file}")
    private String redissonYamlPath;

    @Bean
    RedissonClient config() {
        Config config = null;
        try {
            config = Config.fromYAML(new File(redissonYamlPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Redisson.create(config);
    }
}

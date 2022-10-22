package com.cskaoyan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cskaoyan.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/20 11:27
 */
@Slf4j
@Service
public class RedissonServiceImpl implements RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void queryConfig() {
        Config config = redissonClient.getConfig();
        System.out.println(config.getCodec());
        System.out.println(config.getTransportMode());
        log.info("redisson config: {}", config);
    }

    @Override
    public void lock(String lockName) {
        if (StrUtil.isBlank(lockName)) {
            return;
        }

        RLock lock1 = redissonClient.getLock(lockName);
        lock1.lock(5, TimeUnit.SECONDS);
        log.info("lock1 拿到锁 lock: {}", lock1);

        Thread thread = new Thread(() -> {
            RLock lock2 = redissonClient.getLock(lockName);
            try {
                boolean tryLock = lock2.tryLock(4, 5, TimeUnit.SECONDS);
                if (tryLock) {
                    log.info("lock2 拿到锁 lock: {}", lock2);
                } else {
                    log.info("lock2 在指定时间内没拿到锁, 直接放弃 lock2: {}", lock2);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

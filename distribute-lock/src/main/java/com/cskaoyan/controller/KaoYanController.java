package com.cskaoyan.controller;

import com.cskaoyan.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/20 11:21
 */
@Slf4j
@RestController
@RequestMapping("kaoyan")
public class KaoYanController {

    @Autowired
    private RedissonService redissonService;


    @GetMapping("/redisson/config")
    public void queryConfig() {
        log.info("查询 redisson 配置");
        redissonService.queryConfig();
    }

    @PostMapping("/lock")
    public void lock() {
        log.info("测试分布式锁");
        redissonService.lock("League Of Legends Winner");
    }

}

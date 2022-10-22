package com.cskaoyan.service;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/20 11:27
 */
public interface RedissonService {

    /**
     * 查询 redisson 配置
     */
    void queryConfig();

    /**
     * 加锁
     *
     * @param lockName 锁名
     */
    void lock(String lockName);
}

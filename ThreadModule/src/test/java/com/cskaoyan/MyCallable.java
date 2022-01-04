package com.cskaoyan;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author duanqiaoyanyu
 * @date 2021/12/30 下午 05:19
 */
@Slf4j
public class MyCallable implements Callable<Integer> {
    private int sum = 0;


    @Override
    public Integer call() throws Exception {
        log.info("子线程开始计算");
        Thread.sleep(2000);
        for (int i = 0; i < 5000; i++) {
            sum += i;
        }

        return sum;
    }
}

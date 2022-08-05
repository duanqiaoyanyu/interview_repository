package com.cskaoyan;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duanqiaoyanyu
 * @date 2021/12/29 下午 03:27
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.info("捕获异常处理方法: Thread: {}, Throwable: {}", t, e);

        // e.printStackTrace();
        log.info("处理完成");
    }
}

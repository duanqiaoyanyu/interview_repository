package com.cskaoyan;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
//@SpringBootTest
class ThreadModuleApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void createThread() {

        ThreadGroup threadGroup = new ThreadGroup("my_ThreadGroup");
        UserThreadFactory threadFactory = new UserThreadFactory("room_1", threadGroup,
                new MyUncaughtExceptionHandler());
        Thread thread = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                int i = 1 / 0;
            }
        });
        thread.start();
    }

    @Test
    public void tesThreadException() {
        log.info("主线程开始");
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("异常发生之前");
                        int i = 1/0;
                        log.info("异常发生之后");
                    } catch (Exception e) {
                        log.info("在异常所处线程进行捕获处理");
                        e.printStackTrace();
                        throw e;
                    }
                }
            }).start();
        } catch (Exception e) {
            log.info("异常处理");
            e.printStackTrace();
        }
        log.info("主线程结束");
    }

    private void divideZero() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("异常执行前");
                int i = 1 / 0;
                log.info("i = {}", i);
                log.info("异常发生后");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}

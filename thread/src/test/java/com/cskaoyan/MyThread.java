package com.cskaoyan;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duanqiaoyanyu
 * @date 2022/01/04 下午 03:41
 */
@Slf4j
public class MyThread {

    private static int variable = 0;

    /**
     * thread.join()方法在哪个线程调用的, 就把thread对应线程加在 主线程的前面
     * 所以主线程必须等待 thread的run方法执行完, 才会执行主线程
     *
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        log.info("la_la_la");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("222222");
                log.info("333333333");
            }
        });
        thread.start();
        thread.join();
        log.info("1111111");

    }

    @Test
    public void  test2() {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                variable = 10;
                log.info("variable: {}", variable);
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                variable = variable + 1;
                log.info("variable: {}", variable);
            }
        };

        Thread thread1 = new Thread(runnable1);
        thread1.setPriority(3);

        Thread thread2 = new Thread(runnable2);
        thread2.setPriority(6);

        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 10, TimeUnit.MINUTES, workQueue);
        threadPoolExecutor.execute(thread1);
        threadPoolExecutor.execute(thread2);
    }
}

package com.cskaoyan;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/12 16:22
 */
public class CountDownLatchTest {

    @Test
    public void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable runnable = () -> {
            try {
                Thread.sleep(100);
                System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
            countDownLatch.countDown();
        };

        Thread thread1 = new Thread(runnable, "1");
        Thread thread2 = new Thread(runnable, "2");

        thread1.start();
        thread2.start();
        System.out.println("主线程等待 2 个子线程执行完毕");
        countDownLatch.await();
        System.out.println("2 个子线程已经执行完毕");
        System.out.println("继续执行主线程");
    }
}

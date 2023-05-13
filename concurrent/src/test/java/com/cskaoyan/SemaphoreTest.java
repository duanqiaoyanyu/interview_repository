package com.cskaoyan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/13 15:15
 */
public class SemaphoreTest {

    /**
     * 机器数目, 实际上就是信号量为 5, 非公平模式
     */
    private static Semaphore semaphore = new Semaphore(5, false);
    /**
     * 机器数目, 实际上就是信号量为 5, 公平模式
     */
//    private static Semaphore semaphore = new Semaphore(5, true);

    /**
     * 工人数量, 10
     */
    private static final int NUM = 10;

    /**
     * 当所有工人都完成任务, 那么统计工作量
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(NUM);

    /**
     * 当前时间
     */
    private static final long NOW = System.nanoTime();

    /**
     * 纳秒单位
     */
    private static final long NANOUNIT = 1000000000;

    /**
     * 工作量
     */
    private static final LongAdder WORKLOAD = new LongAdder();

    private static class Worker implements Runnable {
        public Worker(int num) {
            this.num = num;
        }

        private int num;
        private long timed = 20 * NANOUNIT;

        @Override
        public void run() {
            while (true) {
                // 获取信号量
                try {
                    if (semaphore.tryAcquire(timed, TimeUnit.NANOSECONDS)) {
                        System.out.println("工人" + this.num + "占用一个机器在生产...");
                        // 占用一定时间
                        LockSupport.parkNanos((long) (NANOUNIT * num * 0.5));
                        // 统一调整为 2 秒, 将会看到更明显的 semaphore 效果
                        //LockSupport.parkNanos(NANOUNIT * 2);

                        System.out.println("工人" + this.num + "生产完毕, 释放出机器");
                        // 释放信号量
                        // 每个工人最多执行 20 秒
                        WORKLOAD.increment();
                        if ((timed = timed - (System.nanoTime() - NOW)) <= 0) {
                            semaphore.release();
                            countDownLatch.countDown();
                            break;
                        }

                        semaphore.release();
                    } else {
                        countDownLatch.countDown();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < NUM; i++) {
            executorService.execute(new Worker(i));
        }

        executorService.shutdown();
        countDownLatch.await();
        System.out.println("工作完毕, 空闲机器为:" + semaphore.availablePermits());
        System.out.println("总工作量为: " + WORKLOAD.sum());
    }
}

package com.cskaoyan;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/12 19:00
 */
public class CyclicBarrierTest {

    /**
     * 四个初始化参数
     */
    private static volatile int a, b, c, d = -1;
    /**
     * 执行轮次
     */
    private final static int NUM = 2;

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new HookMethod());

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Run1());
        executorService.execute(new Run2());
        executorService.execute(new Run3());
        executorService.shutdown();
    }

    /**
     * 任务1: 初始化 a
     * 等到变量都初始化完毕之后执行: a - b -c
     *
     */
    private static class Run1 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= NUM; i++) {
                // 假设每个初始化任务耗费不等的时间
                int j = ThreadLocalRandom.current().nextInt(5);
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(j));
                a = j;
                System.out.println("a 第" + i + "次初始化 = " + j);
                // 等待
                try {
                    System.out.println(Thread.currentThread().getName() + ": await");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("任务: a - b - c + d = " + (a - b - c + d));
            }
        }
    }

    /**
     * 任务2: 初始化 b
     * 等到变量都初始化完毕之后执行: a + b -c
     */
    private static class Run2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= NUM; i++) {
                // 假设每个初始化任务耗费不等的时间
                int j = ThreadLocalRandom.current().nextInt(5);
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(j));
                b = j;
                System.out.println("b 第" + i + "次初始化 = " + j);
                // 等待
                try {
                    System.out.println(Thread.currentThread().getName() + ": await");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("任务: a + b - c -d = " + (a + b - c - d));
            }
        }
    }

    /**
     * 任务3: 初始化c
     * 等到变量都初始化完毕之后执行: a - b + c
     *
     */
    private static class Run3 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= NUM; i++) {
                // 假设每个初始化任务耗费不等的时间
                int j = ThreadLocalRandom.current().nextInt(5);
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
                c = j;
                System.out.println("c 第" + i + "次初始化 = " + j);
                // 等待
                try {
                    System.out.println(Thread.currentThread().getName() + ": await");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("任务: a - b + c + d = " + (a - b + c + d));
            }
        }
    }

    /**
     * 回调任务: 等到变量都初始化完毕之后执行: d = a + b + c
     *
     */
    private static class HookMethod implements Runnable {
        @Override
        public void run() {
            d = a + b + c;
            System.out.println("\n回调任务: d = a + b + c = " + d);
        }
    }
}

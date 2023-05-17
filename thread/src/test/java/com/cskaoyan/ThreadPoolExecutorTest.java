package com.cskaoyan;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/17 11:08
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<>(8));
        threadPoolExecutor.submit(() -> System.out.println(1111));
    }

    /**
     * 使用给定的初始化参数创建新的 ThreadPoolExecutor
     *
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 空闲线程等待超时时间
     * @param unit 超时时间单位
     * @param workQueue 阻塞任务队列
     * @param threadFactory 线程工厂
     * @param handler 拒绝策略
     */
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory,
//                              RejectedExecutionHandler handler) {
//        // 一系列参数校验
//        /**
//         * 如果核心线程数小于0
//         * 或者 如果最大线程数小于等于0
//         * 或者 最大线程数小于核心线程数
//         * 或者 如果空闲线程等待超时时间小于0
//         *
//         * 满足上面一项, 都将抛出 IllegalArgumentException
//         */
//        if (corePoolSize < 0 ||
//                maximumPoolSize <= 0 ||
//                maximumPoolSize < corePoolSize ||
//                keepAliveTime < 0)
//            throw new IllegalArgumentException();
//        /**
//         * 如果阻塞任务队列为 null
//         * 或者 如果线程工程为 null
//         * 或者 如果拒绝策略为 null
//         *
//         * 满足上面一项, 都将抛出 NullPointerException 异常
//         */
//        if (workQueue == null || threadFactory == null || handler == null)
//            throw new NullPointerException();
//        // 初始化核心线程数
//        this.corePoolSize = corePoolSize;
//        // 初始化最大线程数
//        this.maximumPoolSize = maximumPoolSize;
//        // 初始化阻塞任务队列
//        this.workQueue = workQueue;
//        // 初始化空闲线程等待超时时间
//        this.keepAliveTime = unit.toNanos(keepAliveTime);
//        // 初始化线程工厂
//        this.threadFactory = threadFactory;
//        // 初始化拒绝策略
//        this.handler = handler;
//    }
}

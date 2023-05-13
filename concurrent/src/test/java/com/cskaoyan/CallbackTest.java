package com.cskaoyan;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/13 17:10
 */
public class CallbackTest {

    public static void main(String[] args) {
        // 第一种方式: Future + ExecutorService
//        Task task = new Task();
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        Future<Integer> future = executorService.submit(task);
//        executorService.shutdown();

        // 第二种: FutureTask + ExecutorService
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        Task task = new Task();
//        FutureTask<Integer> futureTask = new FutureTask<>(task);
//        executorService.submit(futureTask);
//        executorService.shutdown();

        // 第三种方式: FutureTask + Thread
        // 新建 FutureTask, 需要一个实现了 Callable 接口的类的实例作为构造函数参数
        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        // 新建 Thread 对象并启动
        Thread thread = new Thread(futureTask);
        thread.setName("MyTask thread");
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread[" + Thread.currentThread().getName() + "] is running");
        // 调用 isDone() 判断任务是否结束
        if (!futureTask.isDone()) {
            System.out.println("Task is not done");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int result = 0;
        try {
            // 调用 get() 方法获取任务结果, 如果任务没有执行完成则阻塞等待
            result = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("result is " + result);
    }

    // 1. 实现 Callable 接口, 实现 call() 方法, 泛型参数为要返回的类型
    private static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("Thread[" + Thread.currentThread().getName() + "] is running");
            int result = 0;
            for (int i = 0; i < 100; i++) {
                result += i;
            }

            Thread.sleep(3000);
            return result;
        }
    }
}

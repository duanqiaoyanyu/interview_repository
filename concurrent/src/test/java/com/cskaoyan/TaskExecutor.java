package com.cskaoyan;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskExecutor {

    // 这种取消会有并发问题, 可能会出现有多个任务都完成了的情况, 那么如何确保只有一个任务执行成功呢, 然后其他任务都停止。

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<?>> futures = new ArrayList<>();

        // 提交任务
        for (int i = 0; i < 5; i++) {
            Callable<Integer> task = createTask(i);
            Future<?> future = executor.submit(task);
            futures.add(future);
        }

        // 等待任务完成
        boolean finished = false;
        while (!finished) {
            for (Future<?> future : futures) {
                if (future.isDone()) {
                    System.out.println(future.get());
                    // 取消其他任务
                    for (Future<?> remainingFuture : futures) {
                        if (!remainingFuture.isDone()) {
                            remainingFuture.cancel(true);
                        }
                    }
                    finished = true;
                    break;
                }
            }
        }

        // 关闭线程池
        executor.shutdown();
    }

    private static Callable<Integer> createTask(final int taskId) {
        return () -> {
            // 模拟任务执行
            Integer duration = null;
            try {
                //Thread.sleep(taskId * 1000);
                duration = RandomUtil.randomInt(2, 8) * 1000;
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                // 捕获取消异常
                System.out.println("Task " + taskId + " was cancelled.   duration" + duration);
                return duration;
            }

            System.out.println("Task " + taskId + " completed.   duration" + duration);
            return duration;
        };
    }
}

package com.cskaoyan;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/15 15:26
 */
public class CompletableFutureTest {

    // 基于 Future 实现
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        // 创建任务 T2 的 FutureTask
//        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
//        // 创建任务 T1 的 FutureTask
//        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));
//
//        // 线程 T1 执行任务 ft2
//        Thread t1 = new Thread(ft2);
//        t1.start();
//        // 线程 T2 执行任务 ft1
//        Thread t2 = new Thread(ft1);
//        t2.start();
//        // 等待线程 t1 执行结果
//        System.out.println(ft1.get());
//    }
//
//    // T1Task 需要执行的任务:
//    // 洗水壶、烧开水、泡茶
//    private static class T1Task implements Callable<String> {
//        FutureTask<String> ft2;
//        // T1 任务需要 T2 任务的 FutureTask
//        T1Task(FutureTask<String> ft2) {
//            this.ft2 = ft2;
//        }
//
//        @Override
//        public String call() throws Exception {
//            System.out.println("T1: 洗水壶...");
//            TimeUnit.SECONDS.sleep(1);
//
//            System.out.println("T1: 烧开水...");
//            TimeUnit.SECONDS.sleep(15);
//            // 获取 T2 线程的茶叶
//            String ft2Get = ft2.get();
//            System.out.println("T1: 拿到茶叶" + ft2Get);
//
//            System.out.println("T1: 泡茶...");
//            return "上茶:" + ft2Get;
//        }
//    }
//
//    // T2Task 需要执行的任务:
//    // 洗茶壶、洗茶杯、拿茶叶
//    private static class T2Task implements Callable<String> {
//        @Override
//        public String call() throws Exception {
//            System.out.println("T2: 洗茶壶...");
//            TimeUnit.SECONDS.sleep(1);
//
//            System.out.println("T2: 洗茶杯...");
//            TimeUnit.SECONDS.sleep(2);
//
//            System.out.println("T2: 拿茶叶...");
//            TimeUnit.SECONDS.sleep(1);
//
//            return "龙井";
//        }
//    }

    public static void main(String[] args) {
        // 任务1: 洗水壶 -> 烧开水
        CompletableFuture<Void> ft1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1: 洗水壶...");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T1: 烧开水...");
            sleep(15, TimeUnit.SECONDS);
        });

        // 任务2: 洗茶壶 -> 洗茶杯 -> 拿茶叶
        CompletableFuture<String> ft2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2: 洗茶壶...");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T2: 洗茶杯...");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2: 拿茶叶...");
            sleep(1, TimeUnit.SECONDS);

            return "龙井";
        });

        // 任务3: 任务1 和 任务2 完成后执行: 泡茶
        CompletableFuture<String> f3 = ft1.thenCombine(ft2, (unused, tf) -> {
            System.out.println("T1: 拿到茶叶:" + tf);
            System.out.println("T1: 泡茶...");
            return "上茶" + tf;
        });

        // 等待任务3执行结果
        System.out.println(f3.join());
    }

    private static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {

        }
    }
}

package com.cskaoyan;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duanqiaoyanyu
 * @date 2021/12/30 下午 03:19
 */
@Slf4j
//@SpringBootTest
public class CallableTest {

    @Test
    public void testXxx() throws InterruptedException {
        log.info("开始测试");
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 1, 10, TimeUnit.MINUTES, workQueue);
        Future<Integer> future = threadPoolExecutor.submit(new MyCallable());
        Thread.sleep(1000);
        System.out.println("---------");
        Integer integer = null;
        try {
            integer = future.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("2222222");
    }

    @Test
    public void testFutureTask() {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 1, 10, TimeUnit.MINUTES, workQueue);
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
        threadPoolExecutor.submit(futureTask);
        threadPoolExecutor.shutdown();
        try {
            Thread.sleep(1000);
            System.out.println("主线程在执行其他任务");
            Integer integer = futureTask.get();
            System.out.println(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完成");
    }
}

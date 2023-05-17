package com.cskaoyan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/17 17:43
 */
public class ExecutorsTest {

    public static void main(String[] args) {
        // 创建的基本都是 无界阻塞队列, 所以任务过多时可能会导致 OOM
        ExecutorService executorService = Executors.newCachedThreadPool();
    }
}

package com.cskaoyan;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duanqiaoyanyu
 * @date 2021/12/29 下午 03:12
 */
public class UserThreadFactory implements ThreadFactory {

    private final String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger(1);
    private ThreadGroup threadGroup;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    // 定义线程组名称，在利用 jstack 来排查问题时，非常有帮助
    UserThreadFactory(String whatFeatureOfGroup, ThreadGroup threadGroup, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        namePrefix = "From UserThreadFactory's " + whatFeatureOfGroup + "-Worker-";
        this.threadGroup = threadGroup;
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public Thread newThread(Runnable task) {
        String name = namePrefix + nextId.getAndIncrement();
        Thread thread = new Thread(threadGroup, task, name, 0);
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        System.out.println(thread.getName());
        return thread;
    }
}

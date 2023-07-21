package com.iwhalecloud.web.utils;

import java.util.concurrent.*;

public class ThreadPoolManager {

    private static volatile ThreadPoolExecutor executor;

    private ThreadPoolManager() {}

    public static ThreadPoolExecutor getThreadPool() {
        if (executor == null) {
            synchronized (ThreadPoolManager.class) {
                if (executor == null) {
                    initThreadPool();
                }
            }
        }
        return executor;
    }

    private static void initThreadPool() {
        int corePoolSize = 8;
        int maxPoolSize = 16;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(500);
        ThreadFactory factory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10, TimeUnit.SECONDS, workQueue, factory, handler);
        executor.allowCoreThreadTimeOut(true);
    }
}

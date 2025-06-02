package com.mochi.demo.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 常见线程池
 */
public class FrequentPool {

    public void cachePool() {
        ExecutorService cachedPool = Executors.newCachedThreadPool();
    }

    public void fixedPool() {
        ExecutorService fixedPool = Executors.newFixedThreadPool(7);
    }

    public void scheduledPoll() {
        // ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(7);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(7);
//        executor.schedule()
    }

    public void singlePool() {
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
    }
}

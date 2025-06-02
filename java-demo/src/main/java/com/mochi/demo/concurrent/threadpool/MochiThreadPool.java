package com.mochi.demo.concurrent.threadpool;

import cn.hutool.core.thread.NamedThreadFactory;

import java.util.concurrent.*;

/**
 *
 * 手动创建线程池
 */
public class MochiThreadPool {

    /**
     * *********** pool需要声明为volatile ***********
     */
    private static volatile MochiThreadPool pool;

    private static ThreadPoolExecutor executor;

    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

    private static final Integer corePoolSize = 1, maximumPoolSize = 2;
    /**
     * 对于超出核心线程数的线程，空闲时间一旦达到存活时间，就会被销毁
     */
    private static final Long keepAliveTime = 500L;

    private MochiThreadPool(String threadName, Boolean isDaemon) {
        if (executor == null) {
            executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                    workQueue, new NamedThreadFactory(threadName, isDaemon),
                    new ThreadPoolExecutor.AbortPolicy()) {
                @Override
                protected void beforeExecute(Thread t, Runnable r) {
                    super.beforeExecute(t, r);
                }

                @Override
                protected void afterExecute(Runnable r, Throwable t) {
                    super.afterExecute(r, t);
                }
            };

        }
    }

    public static MochiThreadPool getInstance(String threadName, Boolean isDaemon) {
        if (pool == null) {
            synchronized (MochiThreadPool.class) {
                if (pool == null) {
                    pool = new MochiThreadPool(threadName, isDaemon);
                }
            }
        }
        return pool;
    }

    /**
     * 在执行Runnable任务时, 直接调用execute方法执行 异常会抛出
     * @param runnable
     */
    public void executeRunnbale(Runnable runnable) {
        // executor.submit(runnable);
        executor.execute(runnable);
    }

    /**
     * 在执行callable任务时 需要有返回结果, 直接调用submit方法执行 不会抛出异常
     * future.get()方法会阻塞
     * @param callable
     * @return
     */
    public Future submitCallbale(Callable callable) {
        return executor.submit(callable);
    }

}

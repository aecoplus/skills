package com.mochi.demo.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo extends Thread {

    public static void main(String[] args) {
        /**
         * 线程池的物种状态：
         * RUNNING：运行状态，线程池创建好之后就会进入此状态，如果不手动调用关闭方法，那么线程池在整个程序运行期间都是此状态。
         * SHUTDOWN：关闭状态，不再接受新任务提交，但是会将已保存在任务队列中的任务处理完。
         * STOP：停止状态，不再接受新任务提交，并且会中断当前正在执行的任务、放弃任务队列中已有的任务。
         * TIDYING：整理状态，所有的任务都执行完毕后（也包括任务队列中的任务执行完），当前线程池中的活动线程数降为0时的状态。到此状态之后，会调用线程池的terminated()方法。
         * TERMINATED：销毁状态，当执行完线程池的terminated()方法之后就会变为此状态。
         */


    }

    /**
     * 常见线程池
     */
    private static void test1() {

        /**
         * FixedThreadPool：固定大小的线程池，当有新任务提交时，如果线程池中有空闲线程，则立即使用空闲线程执行任务，如果没有空闲线程，则新任务会在一个队列中等待，直到有线程空闲出来。
         *                     创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         * CachedThreadPool：可缓存的线程池，这种线程池中的线程数量会根据需要动态调整。如果线程池中的线程在一段时间内没有被使用，那么这些线程就会被终止，从而节省系统资源。当有新任务提交时，如果线程池中有空闲线程，则立即使用空闲线程执行任务，如果没有空闲线程，则创建新的线程来执行任务。
         *                     创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
         * ScheduledThreadPool：定时或周期性执行任务的线程池。这种线程池中的线程数量是固定的，主要用于执行定时任务或周期性任务。
         *
         * SingleThreadExecutor：单线程的线程池，这种线程池只有一个线程来执行任务。当有多个任务提交时，这些任务会按照提交的顺序依次执行。
         *                      创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         */
    }

    private static void test2() {
        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 提交10个任务给线程池执行
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
            });
        }

        // 关闭线程池（这会导致已经提交的任务继续执行，但不再接受新任务）
        executor.shutdown();
        while (!executor.isTerminated()) {
            // 等待所有任务执行完毕
        }

        System.out.println("All tasks have been completed.");
    }

}

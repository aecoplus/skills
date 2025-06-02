package com.mochi.demo.concurrent.thread.base;

/**
 * JVM创建的线程与操作系统线程是1:1的关系
 * 操作系统线程主要有以下三个状态：
 * 1. 就绪状态(ready)：线程正在等待使用CPU，经调度程序调用之后可进入running状态。
 * 2. 执行状态(running)：线程正在使用CPU。
 * 3. 等待状态(waiting): 线程经过等待事件的调用或者正在等待其他资源（如I/O）。
 */
public class ThreadStatusTest {
    // Thread.State 源码
    public enum State {
        /**
         * 初始状态  只存在堆内存中, 与常规对象无异
         */
        NEW,
        /**
         * 表示当前线程正在运行中。处于RUNNABLE状态的线程在Java虚拟机中运行，也有可能在等待其他系统资源（比如I/O）
         * Java线程的RUNNABLE状态其实是包括了传统操作系统线程的ready和running两个状态的。
         */
        RUNNABLE,
        /**
         * 阻塞状态。处于BLOCKED状态的线程正等待锁的释放以进入同步区
         */
        BLOCKED,
        /**
         * 等待状态。处于等待状态的线程变成RUNNABLE状态需要其他线程唤醒(notify)
         * 调用如下3个方法会使线程进入等待状态: Object.wait()   Thread.join()  Thread.join()
         */
        WAITING,
        /**
         * 超时等待状态。线程等待一个具体的时间，时间到后会被自动唤醒。
         * 调用如下方法会使线程进入超时等待状态：
         */
        TIMED_WAITING,
        /**
         * 终止状态。此时线程已执行完毕。
         */
        TERMINATED;
    }

    public static void main(String[] args) {
//        Thread.State
        Thread thread = new Thread(() -> {});
        thread.start();
        thread.start(); // throw Exception
        /**
         * 多次调用start方法 会抛出IllegalThreadStateException异常
         * 在start方法内部校验，只有Thread.status == 0 的thread对象才能调用start方法
         * so,
         * 反复调用同一个线程的start()方法是否可行？ Answer: NO
         * 假如一个线程执行完毕（此时处于TERMINATED状态），再次调用这个线程的start()方法是否可行？Answer: NO
         */
    }
}

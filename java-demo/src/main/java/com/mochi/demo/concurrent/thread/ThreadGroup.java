package com.mochi.demo.concurrent.thread;

public class ThreadGroup {
    /**
     * 每个Thread必然存在于一个ThreadGroup中
     * Thread不能独立于ThreadGroup存在。
     * 执行main()方法线程的名字是main，如果在new Thread时没有显式指定，
     * 那么默认将父线程（当前执行new Thread的线程）线程组设置为自己的线程组。
     */

    /**
     * Java程序中对线程所设置的优先级只是给操作系统一个建议，操作系统不一定会采纳。
     * 而真正的调用顺序，是由操作系统的线程调度算法决定的
     */

    /**
     * 如果某个线程优先级大于线程所在线程组的最大优先级,
     * 那么该线程的优先级将会失效, 取而代之的是线程组的最大优先级
     */
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getThreadGroup().getName());
    }
}

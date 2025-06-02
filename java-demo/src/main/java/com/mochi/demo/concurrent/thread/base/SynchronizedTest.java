package com.mochi.demo.concurrent.thread.base;

public class SynchronizedTest {
    /**
     * 关键字在实例方法上，锁为当前实例
     */
    public synchronized void instanceLock() {
        // code
    }

    /**
     * 关键字在静态方法上，锁为当前Class对象
     */
    public static synchronized void classLock() {
        // code
    }

    /**
     * 关键字在代码块上，锁为括号里面的对象
     */
    public void blockLock() {
        Object o = new Object();
        synchronized (o) {
            // code
        }
    }
}

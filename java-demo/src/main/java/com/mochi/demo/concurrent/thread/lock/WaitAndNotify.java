package com.mochi.demo.concurrent.thread.lock;

/**
 * 等待/通知机制:
 * Java多线程的等待/通知机制是基于Object类的wait()方法和notify(), notifyAll()方法来实现的。
 * notify()方法会随机叫醒一个正在等待的线程，而notifyAll()会叫醒所有正在等待的线程。
 */
public class WaitAndNotify {

    /**
     * 在这个Demo里，线程A和线程B首先打印出自己需要的东西
     * 然后使用notify()方法叫醒另一个正在等待的线程
     * 然后自己使用wait()方法陷入等待并释放lock锁。
     */

    private static Object lock = new Object();

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("ThreadA: " + i);
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }
    }
    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("ThreadB: " + i);
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(1000);
        new Thread(new ThreadB()).start();
    }
}

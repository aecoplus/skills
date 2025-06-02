package com.mochi.demo.concurrent.thread.base;


public class ThreadNormalMethod {
    public static void main(String[] args) {
        try {
            Thread.sleep(100L);
            // new Thread(() -> System.out.println("from join!!!")).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 1. 休眠 sleep
         * 当前线程主动休眠millis毫秒
         * public static void sleep(long millis) throws InterruptedException;
         */

        /**
         * 2. 主动放弃 yield
         * 当前线程主动放弃时间片 回到就绪状态 竞争下一次时间片
         * public static void yield();
         */

        /**
         * 3. 加入 join
         * A等待B线程死亡后继续执行
         * public final void join();
         */
    }
}

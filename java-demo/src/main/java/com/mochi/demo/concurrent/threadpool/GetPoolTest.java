package com.mochi.demo.concurrent.threadpool;

public class GetPoolTest {

    public static void main(String[] args) {
        MochiThreadPool pool = MochiThreadPool.getInstance("mochi thread", false);
        pool.executeRunnbale(() -> System.out.println("test mochi thread"));


    }

}

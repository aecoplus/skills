package com.mochi.demo.concurrent.thread.base;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    private enum Result{
        SUCCESS, FAIL, CANCELED
    }

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            // 做长时间的任务
            System.out.println("1");
            return true;
        }).thenAccept((result) -> {
            if (result) {
                // 处理结束流程
                System.out.println(Result.SUCCESS);
                System.out.println(Result.SUCCESS.ordinal());
                System.out.println(Result.FAIL.ordinal());
                System.out.println(Result.CANCELED.ordinal());
            } else {


            }
        });
    }
}

package com.mochi.demo.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * volatile 不能保证原子性;
 *
 */
public class ThreadLocalTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        /**
         * 1. execute是Executor接口的方法，而submit是ExecutorService的方法，并且ExecutorService接口继承了Executor接口。
         * 2. execute只接受Runnable参数，没有返回值；而submit可以接受Runnable参数和Callable参数，并且返回了Future对象，
         *      可以进行任务取消、获取任务结果、判断任务是否执行完毕/取消等操作。
         *      其中，submit会对Runnable或Callable入参封装成RunnableFuture对象，调用execute方法并返回。
         * 3. 通过execute方法提交的任务如果出现异常则直接抛出原异常，是在线程池中的线程中；
         * 而submit方法是捕获了异常的，只有当调用Future的get方法时，才会抛出ExecutionException异常，且是在调用get方法的线程。
         */

//        ThreadPoolExecutor
//        Executor executor: Runnable类型 没有返回值 异常直接抛出
//        ExecutorService submit: Runnable Callable
//        AbstractExecutorService: 封装submit方法, 将参数封装为RunnableFuture
//        ThreadPoolExecutor 最终对execute方法做了实现

        threadLocal.set("jimin");
        System.out.println(threadLocal.get());
        FutureTask<String> stringFutureTask = new FutureTask<>(new MyCallable());
    }

    private static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return null;
        }
    }

}

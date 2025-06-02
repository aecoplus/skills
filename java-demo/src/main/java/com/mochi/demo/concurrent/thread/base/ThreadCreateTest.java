package com.mochi.demo.concurrent.thread.base;

import java.util.concurrent.*;

public class ThreadCreateTest {
    /**
     * 创建线程的三种方式:
     * 1. 继承Thread类 重写run方法
     * 2. 实现Runnable接口
     * 3. 实现Callable接口
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 1. 继承Thread
        MyThread thread = new MyThread("thread extends Thread");
        thread.start();

        // 2. 实现Runnable接口
        Runnable r = new MyRunnable();
        Thread runnable = new Thread(r, "thread implements Runnable");
        runnable.start();

        // 3-1. 实现Callable接口 - Future
        ExecutorService executor1 = Executors.newCachedThreadPool();
        MyCallable callable = new MyCallable();
        // 有返回值 通过result取值 submit(Callable c)
        Future<Integer> result =  executor1.submit(callable);
        // 注意调用get方法会阻塞当前线程，直到得到结果。
        // 所以实际编码中建议使用可以设置超时时间的重载get方法。
        System.out.println(result.get());
        System.out.println(result.get(1000, TimeUnit.MILLISECONDS));

        // 3-2. 实现Callable接口 - FutureTask implements RunnableFuture
        /**
         * FutureTask FutureTask能够在高并发环境下确保任务只执行一次 可以调用runAndReset重新执行
         * FutureTask是实现的RunnableFuture接口的，而RunnableFuture接口同时继承了Runnable接口和Future接口
         * public interface RunnableFuture<V> extends Runnable, Future<V>
         */
        ExecutorService executor2 = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = new FutureTask(new MyCallable());
        // 无返回值  execute(Runnable r)
        executor2.execute(futureTask);
        // 通过futureTask取值 阻塞式
        System.out.println(futureTask.get());

        // Executors.callable()方法会构建一个Callable对象
        FutureTask<String> vFutureTask = new FutureTask<>(new MyRunnable(), "1");


        // execute和submit两种方法的比较:
        // execute方法只能接收Runnable类型的参数; submit可以接收Runnable和Callable类型
        executor1.execute(runnable);
        // execute会抛出异常 submit会吃掉异常(可通过Future的get方法将任务执行时的异常重新抛出)

        // execute没有返回值; submit有返回值


    }

    private void test() {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();

    }


}


/**
 * 方式1：extends Thread
 */
class MyThread extends Thread {

    MyThread(String name) {
        super(name);
    }

    @Override
    public void run (){
        System.out.println("this is thread in [MyThread] ");
        System.out.println("线程Id:" + this.getId() + ", or:"+ Thread.currentThread().getId());
        System.out.println("线程name:" + this.getName() + ", or:"+ Thread.currentThread().getName());
    }
}

/**
 * 方式2 extends Runnable
 */
class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("this is thread in [myRunnable]");
        System.out.println("当前线程Id: " + Thread.currentThread().getId());
        System.out.println("当前线程name:" + Thread.currentThread().getName());
    }
}

/**
 * 方式3 implements Callable<T>
 */
class MyCallable implements Callable<Integer> {

    @Override
    public Integer call()  throws Exception{ // 可以抛出异常
        return 1;
    }
}

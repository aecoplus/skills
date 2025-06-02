private final class Worker extends AbstractQueuedSynchronizer // 线程中断(AQS)
        implements Runnable {
    // 工作线程的thread对象 初始化时构建出来的
    final Thread thread;
    // 需要执行的任务
    Runnable firstTask;
    volatile long completedTasks;

    /**
     * Creates with given first task and thread from ThreadFactory.
     * @param firstTask the first task (null if none)
     */
    Worker(Runnable firstTask) {
        // 刚刚初始化的工作线程是不允许被中断的
        setState(-1); // inhibit interrupts until runWorker
        this.firstTask = firstTask;
        // 给worker构建thread对象
        this.thread = getThreadFactory().newThread(this);
    }

    // 调用t.start()时 执行当前run方法
    public void run() {
        runWorker(this);
    }
}
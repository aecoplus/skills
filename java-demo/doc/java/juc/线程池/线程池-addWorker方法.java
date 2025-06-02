// addWorkder方法
private boolean addWorker(Runnable firstTask, boolean core) {
    // 外层判断线程池状态 内存判断线程池工作线程数量
    // retry 外层for循环的标识
    retry:
    for (;;) {
        // 根据ctl的值拿到线程池的状态rs
        int c = ctl.get();
        int rs = runStateOf(c);

        // 线程池状态不是RUNNING 再次做后续判断 查看当前任务是否可以不处理
        if (rs >= SHUTDOWN &&
            // 线程池状态为SHUTDOWN 且任务为空 且队列不为空的情况时 继续向下执行
            ! (rs == SHUTDOWN && firstTask == null && !workQueue.isEmpty()))
            return false;
        for (;;) {
            // 基于ctl获取线程池的工作线程数量
            int wc = workerCountOf(c);
            // 判断线程数量是否大于最大值
            if (wc >= CAPACITY ||
                // 如果是核心线程,判断是否大于corePoolSize; 如果是非核心线程,判断是否大于maximumPoolSize
                wc >= (core ? corePoolSize : maximumPoolSize))
                return false;
            // 以CAS的方式 对工作线程数+1; 如果成功 直接跳出外层for循环
            if (compareAndIncrementWorkerCount(c))
                break retry;
            c = ctl.get();  // Re-read ctl
            // 基于新获取的ctl拿到线程池状态,判断和之前的状态是否一致
            if (runStateOf(c) != rs)
                // 不一致说明并发导致线程池状态发生变化, 需重新判断线程池状态
                continue retry;
            // else CAS failed due to workerCount change; retry inner loop
        }
    }

    // 添加工作线程 并启动工作线程
    boolean workerStarted = false;
    boolean workerAdded = false;
    Worker w = null; // 工作线程
    try {
        // new Worker构建工作线程 并将任务扔到Worker中
        w = new Worker(firstTask);
        // 拿到worker中绑定的thread线程
        final Thread t = w.thread;
        if (t != null) { // 健壮性判断
            // 加锁
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                // 基于重新获取的ctl 拿到线程池的状态
                int rs = runStateOf(ctl.get());
                // 如果线程池状态为RUNING 则添加工作线程
                if (rs < SHUTDOWN ||
                    // 如果线程池状态为SHUTDOWN 且任务为空 则添加工作线程
                    (rs == SHUTDOWN && firstTask == null)) {
                    if (t.isAlive()) // 线程是否start了(健壮性判断)
                        throw new IllegalThreadStateException();
                    workers.add(w); // workers为HashSet
                    int s = workers.size();
                    if (s > largestPoolSize) // largestPoolSize记录线程池中历史最大线程数量
                        largestPoolSize = s;
                    // 标识设置为true
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if (workerAdded) {
                // 启动线程 并且将标识设置为true
                t.start();
                workerStarted = true;
            }
        }
    } finally {
        // 如果启动工作线程失败
        if (! workerStarted)
            // 移除worker; 工作线程数-1; 尝试将线程池状态改为TIDYING
            addWorkerFailed(w);
    }
    return workerStarted;
}

// addWorkerFailed 对添加线程池失败的一个不就措施
private void addWorkerFailed(Worker w) {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
        // 判断之前添加工作线程是否成功
        if (w != null)
            // 如果成功 将workers中的当前工作线程移除掉
            workers.remove(w);
        // 将工作线程数量-1
        decrementWorkerCount();
        // 将线程池的工作状态变为TIDYING
        tryTerminate();
    } finally {
        mainLock.unlock();
    }
}
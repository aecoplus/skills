// 线程池-processWorkerExit
private void processWorkerExit(Worker w, boolean completedAbruptly) {
    if (completedAbruptly) // 调用不合法(钩子函数异常)
        decrementWorkerCount();

    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
    	// =========记录当前线程池一共执行了多少任务=========
        completedTaskCount += w.completedTasks;
        workers.remove(w); // =========移除工作线程=========
    } finally {
        mainLock.unlock();
    }
    // ==========尝试将线程池关闭(TIDYING->TERMINATED)=========
    tryTerminate();
    int c = ctl.get();
    if (runStateLessThan(c, STOP)) { // 线程池为RUNNING或SHUTDOWN
        if (!completedAbruptly) { // 正常状态移除worker
        	// 核心线程数最小值
            int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
            // 核心线程数为0, 且任务队列不为空; 则最小核心线程数改为1
            if (min == 0 && ! workQueue.isEmpty())
                min = 1;
            // 工作线程数是否大于最小核心线程数, false -> addWorker
            if (workerCountOf(c) >= min)
                return; // replacement not needed
        }
        // ===========非正常状态移除worker, 再追加一个worker=========
        addWorker(null, false);
    }
}
// 线程池-getTask方法 (如何从阻塞队列中获取任务)
private Runnable getTask() {
    boolean timedOut = false; // 标识-拿不到任务超时(非核心线程可以被干掉)
    for (;;) {
    	// ========== 判断线程池状态 ==========
        int c = ctl.get();
        int rs = runStateOf(c);
        // 线程池状态为SHUTDOWN 且阻塞队列为空; 或者线程池状态>=STOP
        if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
            decrementWorkerCount(); // 移除当前的工作线程
            return null; // 返回null, 交由processWorkerExit移除当前工作线程
        }
        // ========== 判断工作线程数量 ==========
        int wc = workerCountOf(c); // 线程池的工作线程数
        // 是否允许核心线程超时(default false);
        // 工作线程数>核心线程数
        boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
        // 工作线程数 > 最大线程数; 工作线程数 > 核心线程数, 且当前线程超时
        if ((wc > maximumPoolSize || (timed && timedOut))
        	// 工作线程数至少为2, 或阻塞队列为空
            && (wc > 1 || workQueue.isEmpty())) {
        	// 基于CAS的方式工作线程数-1, 只有一个线程会CAS成功
            if (compareAndDecrementWorkerCount(c))
                return null; // 返回null, 交由processWorkerExit移除当前工作线程
            continue;
        }
		// ========== 从阻塞队列中获取任务 ==========
        try {
            Runnable r = timed ?
            	// (非核心线程(理解上))阻塞一定时间 从任务列表中获取任务
                workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                // (核心线程(理解上)) 一直阻塞! (可能会被中断)
                workQueue.take();
            if (r != null) // 拿到任务直接返回执行
                return r;
            timedOut = true; // 获取任务超时(达到了worker的最大生存时间)
        } catch (InterruptedException retry) {
            timedOut = false;
        }
    }
}
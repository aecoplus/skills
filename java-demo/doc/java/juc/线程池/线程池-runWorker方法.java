// runWorker方法
final void runWorker(Worker w) {
	// 拿到当前工作线程->worker
    Thread wt = Thread.currentThread();
    Runnable task = w.firstTask; // 拿到worker中封装的任务
    w.firstTask = null; // 归位
    w.unlock(); // allow interrupts,将worker的state归为0,代表worker可以被中断
    boolean completedAbruptly = true; // 任务执行时 钩子函数是否抛出异常
    try {
    	// 获取任务方式一, 执行execute/submit时传入的任务直接处理
    	// 获取任务方式二, 从阻塞队列中获取任务来执行
        while (task != null || (task = getTask()) != null) {
        	// 加锁: 在SHUTDOWN状态下 当前线程是不允许被中断的
        	// 且Worker内部实现的锁不是可重入锁(因为在中断时,也需要对worker进行lock),不能获取就代表worker正在执行任务
            w.lock();
            // 如果线程池状态为STOP, 则将当前worker中断
            // 第一个判断, 线程池当前状态是否是STOP
            // 第二个判断, 查看worker是否被中断(false-未中断, true-已中断), 并归位(总会设为false);
            if ((runStateAtLeast(ctl.get(), STOP) || (Thread.interrupted() && runStateAtLeast(ctl.get(), STOP)))
            	&& !wt.isInterrupted()) // 当前worker中断标记位是否为false
                wt.interrupt();
            try {
            	// 执行任务的钩子函数
                beforeExecute(wt, task);
                Throwable thrown = null;
                try {
                    task.run(); // 执行任务...
                } catch (RuntimeException x) {
                    thrown = x; throw x;
                } catch (Error x) {
                    thrown = x; throw x;
                } catch (Throwable x) {
                    thrown = x; throw new Error(x);
                } finally {
                    afterExecute(task, thrown);
                }
            } finally {
                task = null; // 将任务置为null
                w.completedTasks++; // 执行成功的任务个数+1
                w.unlock(); // 将worker的state标记位设置为0
            }
        }
        completedAbruptly = false;
    } finally {
        processWorkerExit(w, completedAbruptly);
    }
}
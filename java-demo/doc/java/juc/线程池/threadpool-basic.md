### ThreadPoolExecutor

Java中的线程池顶层接口是Executor接口，ThreadPoolExecutor是这个接口的实现类

```java
线程池的构造方法 (重载)
public ThreadPoolExecutor(int corePoolSize,
                         int maximumPoolSize,
                         long keepAliveTime,
                         TimeUnit unit,
                         BlockingQueue<Runnable> workQueue,
                         ThreadFactory threadFactory,
                         RejectedExecutionHandler handler)
1. corePoolSize: 该线程池中核心线程数最大值
核心线程：线程池中有两类线程，核心线程和非核心线程。核心线程默认情况下会一直存在于线程池中，即使这个核心线程什么都不干(铁饭碗)
非核心线程如果长时间的闲置，就会被销毁（临时工）。
当向线程池提交一个任务时，若线程池已创建的线程数小于corePoolSize，即便此时存在空闲线程，也会通过创建一个新线程来执行该任务,直到已创建的线程数大于或等于corePoolSize时
2. maximumPoolSize: 该线程池中线程总数最大值
该值等于核心线程数量 + 非核心线程数量
当队列满了,且已创建的线程数小于maximumPoolSize,则线程池会创建新的线程来执行任务。另外，对于无界队列，可忽略该参数。
3. keepAliveTime: 非核心线程闲置超时时长
非核心线程如果处于闲置状态超过该值，就会被销毁。如果设置allowCoreThreadTimeOut(true)，则会也作用于核心线程。
当线程池中线程数大于核心线程数时,线程的空闲时间如果超过线程存活时间，那么这个线程就会被销毁,直到线程池中的线程数小于等于核心线程数。
4. unit: keepAliveTime的单位
 TimeUnit是一个枚举类型
5. workQueue: 阻塞队列，维护着等待执行的Runnable任务对象
常用的几个阻塞队列：
LinkedBlockingQueue: 链式阻塞队列，底层数据结构是链表，默认大小是Integer.MAX_VALUE，也可以指定大小。
ArrayBlockingQueue: 数组阻塞队列，底层数据结构是数组，需要指定队列的大小。
SynchronousQueue: 同步队列，内部容量为0，每个put操作必须等待一个take操作，反之亦然。
DelayQueue: 延迟队列，该队列中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素 。
6. threadFactory  创建线程的工厂
用于批量创建线程，统一在创建线程时设置一些参数，如是否守护线程、线程的优先级等。如果不指定，会新建一个默认的线程工厂。
7. handler  拒绝处理策略
线程数量大于最大线程数就会采用拒绝处理策略，四种拒绝处理的策略为:
ThreadPoolExecutor.AbortPolicy：默认拒绝处理策略，丢弃任务并抛出RejectedExecutionException异常。
ThreadPoolExecutor.DiscardPolicy：丢弃新来的任务，但是不抛出异常。
ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列头部（最旧的）的任务，然后重新尝试执行程序(如果再次失败，重复此过程)。
ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务。

```

### ThreadPoolExecutor的策略

```java
ThreadPoolExecutor类中定义了一个volatile int变量runState来表示线程池的状态, 线程池状态:
1. RUNNING = -1
	线程池创建后处于RUNNING状态
2. SHURDOWN = 0
	调用shutdown()方法后处于SHUTDOWN状态，线程池不能接受新的任务，清除一些空闲worker,会等待阻塞队列的任务完成。
3. STOP = 1
	调用shutdownNow()方法后处于STOP状态，线程池不能接受新的任务，中断所有线程，阻塞队列中没有被执行的任务全部丢弃; 此时,poolsize=0,阻塞队列的size也为0
4. TIDYING = 2 (译: 使整洁)
	当所有的任务已终止，ctl记录的”任务数量”为0，线程池会变为TIDYING状态。接着会执行terminated()函数。
	(ThreadPoolExecutor中有一个控制状态的属性叫ctl，它是一个AtomicInteger类型的变量)
5. TERMINATED = 3
	线程池处在TIDYING状态时，执行完terminated()方法之后，就会由 TIDYING -> TERMINATED， 线程池被设置为TERMINATED状态。
```


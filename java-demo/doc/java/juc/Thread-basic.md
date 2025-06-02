### 进程 线程

```
进程(process)是操作系统资源(CPU、内存)分配的基本单位 线程是CPU的基本调度单位
进程间不能共享数据段地址 但是同进程的线程间可以
```

```java
// 线程的基本组成部分:
1. CUP时间片: OS会为每一个线程分配执行时间
2.运行数据:
	堆空间: 存储线程使用的对象 多个线程可以共享堆中的对象
	栈空间: 存储线程使用的局部对象 每个线程都拥有独立的栈
3. 线程执行的逻辑代码
```

```java
// 线程的特点:
1. 线程抢占式执行: 效率高 可防止单一线程长时间占用CPU
2. 在单核CPU中, 宏观上同时执行 微观上顺序执行
```

```java
// Thread 和 Runnable 相比:
1. 由于Java“单继承，多实现”的特性，Runnable接口使用起来比Thread更灵活。
2. Runnable接口出现更符合面向对象，将线程单独进行对象的封装。
3. Runnable接口出现，降低了线程对象和线程任务的耦合性。
4. 如果使用线程时不需要使用Thread类的诸多方法，显然使用Runnable接口更为轻量。
```

```java
// 所谓“临界区”，指的是某一块代码区域，它同一时刻只能由一个线程执行
```

### synchronized

```java
// 1.关键字在实例方法上，锁为当前实例
public synchronized void instanceLock() {}
// 2.关键字在静态方法上，锁为当前Class对象
public static synchronized void classLock() {}
// 3.关键字在代码块上，锁为括号里面的对象
public void blockLock() {
    Object o = new Object();
    synchronized (o) {}
}
```

### volitile

关键字能够保证内存的可见性，如果用volitile关键字声明了一个变量，在一个线程里面改变了这个变量的值，那其它线程是立马可见更改后的值的。

### Callable

```java
Callable: 一般是配合线程池工具ExecutorService来使用的
ExecutorService可以使用submit方法来让一个Callable接口执行 它会返回一个Future

FutureTask: 能够在高并发环境下确保任务只执行一次
FutureTask是实现的RunnableFuture接口的，而RunnableFuture接口同时继承了Runnable接口和Future接口:
public interface RunnableFuture<V> extends Runnable, Future<V>
```


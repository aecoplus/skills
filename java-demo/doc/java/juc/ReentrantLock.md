## ReentrantLock

### 介绍

ReentrantLock是一个互斥锁，可以让多线程执行期间，只有一个线程执行指定的一段代码

```java
ReentrantLock lock = new ReentrantLock(); // 默认是NofairSync (更推荐, 效率高)
lock.lock();  // 加锁
try {
    // ...单线程执行的代码
}finally {
    lock.unlock(); // 解锁
}
```

### ReentrantLock的lock锁

```
FairSync：每个线程在执行lock方法时, 查看是否有线程在排队, 如果有 直接去排队, 如果没有 尝试获取锁资源
NofairSync：每个线程都会在lock时, 先尝试获取锁资源, 获取不到再排队
```

### AQS

AbstractQueuedSynchronizer， AQS内部维护着一个队列(双向链表)

三个核心属性：head tail state

![](F:\mochi\juc\AQS.png)

### lock方法

```java
// 非公平锁
final void lock() {
	// 以CAS的方式将state从0改为1
    if (compareAndSetState(0, 1))
    	// 修改state成功, 即代表获取锁资源成功
    	// 将当前线程设置到exclusiveOwnerThread(AOS), 代表当前线程拿着锁资源(可重入锁相关)
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1); // 排队
}
// 公平锁
final void lock() {
    acquire(1);
}
```

### acquire方法

```java
public final void acquire(int arg) {
	// tryAcquire分为两种实现,
	// 公平锁操作: state为0时,有人排队我就去排; 非0时,如果是锁重入操作,直接获取锁
	// 非公平锁操作: state为0时,直接尝试CAS修改; 非0时,如果是锁重入操作,直接获取锁
	if (!tryAcquire(arg) &&
		// addWaiter: 在线程没有通过tryAcquire拿到锁资源时，将当前线程封装为Node对象,去AQS内部排队
	    // acquireQueued: 当前线程如果是AQS的头节点，就尝试获取锁资源; 如果长时间没拿到锁，尝试将线程挂起
	    acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
	    selfInterrupt();
}
```

### tryAcquire方法

```java
// 非公平锁tryAcquire
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState(); // 拿到AQS的state值
    if (c == 0) { // 当前没有线程占用
    	// 基于CAS尝试占用锁资源
        if (compareAndSetState(0, acquires)) {
        	// exclusiveOwnerThread属性设置为当前线程
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    // 是不是当前线程占用了锁资源
    else if (current == getExclusiveOwnerThread()) {
    	// 锁重入操作
        int nextc = c + acquires;
        if (nextc < 0) // overflow 锁重入是否达到最大值 (健壮性判断 一般不会发生)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}

// 公平锁tryAcquire实现
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
    	// 有没有线程排队
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```


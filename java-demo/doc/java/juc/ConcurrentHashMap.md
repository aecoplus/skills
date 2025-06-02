## ConcurrentHashMap

一个支持高并发更新与查询的哈希表(基于HashMap)。保证map中的数据不被破坏

### 数据结构

数组 链表 红黑树(查询效率高)

### 安全性

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
while (true) {
    Integer count = map.get(COUNT_KEY);
    if (count == null) {
        // putIfAbsent方法(CAS) 将1存储到map中 返回null 代表成功
        if (map.putIfAbsent(COUNT_KEY, 1) == null)
        break;
    } else if (map.replace(COUNT_KEY, count, count+1)) {
        break;
    }
}
```

### PUT方法

```java
// ConcurrentHashMap - putValue方法
final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException();
    // 基于key计算出hashmap值
    int hash = spread(key.hashCode());
    int binCount = 0;
    // 声明tab为当前ConcurrentHashMap的数组
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh;
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();  // 初始化数组
        // 数组已经初始化了 将数据插入到map中
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            // 当前索引没有数据 采用CAS的方式将key-value插入到该位置,成功-return true,失败-继续for循环
            if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value, null)))
                break;                   // no lock when adding to empty bin
        }
        // 省略部分代码...
}
// tabAt([], i): 获取table[i]
// casTabAt([], i, o1, o2): 以CAS的方式 将table中i位置的数据从o1修改为o2
```

### spread方法 （三散列算法  尽量避免hash冲突）

```java
static final int spread(int h) {
    // h无符号右移16未再进行货运算 (h的高16位和低16位进行^运算 使hashcode全量的位参&运算)
    return (h ^ (h >>> 16)) & HASH_BITS; // 保证hashcode的值一定是一个正数, 负数的含义:
}
// static final int MOVED     = -1; // hash for forwarding nodes
// static final int TREEBIN   = -2; // hash for roots of trees
// static final int RESERVED  = -3; // hash for transient reservations
// static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
```

### initTable方法 （初始化数组）

```java
/** initTable */
private final Node<K,V>[] initTable() {
    Node<K,V>[] tab; int sc;
    // 判断数组是否初始化
    while ((tab = table) == null || tab.length == 0) {
        if ((sc = sizeCtl) < 0)
            Thread.yield(); // lost initialization race; just spin
        // sizeCtl>=0 以CAS的方式将sizeCtl设为-1
        else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
            try { // 初始化操作
                if ((tab = table) == null || tab.length == 0) {
                    // 获取数组初始化的长度,如果sc>0则为sc,否则默认为16
                    int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                    @SuppressWarnings("unchecked")
                    Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                    // table初始化完毕
                    table = tab = nt;
                    sc = n - (n >>> 2);
                }
            } finally {
                sizeCtl = sc;
            }
            break;
        }
    }
    return tab;
}
/** sizeCtl:
-1, 数组正在初始化; 
小于-1, 数组正在扩容; 
0, 还未初始化; 
正数, 如果未初始化,代表要初始化的长度; 若已初始化,代表要扩容的阀值
*/
```


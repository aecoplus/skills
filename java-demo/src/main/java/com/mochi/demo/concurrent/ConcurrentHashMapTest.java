package com.mochi.demo.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentHashMapTest {
    private static String COUNT_KEY = "count";

    public static void main(String[] args) {
        chm();
    }

    public static void chm() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        while (true) {
            Integer count = map.get(COUNT_KEY);
            if (count == null) {
                // putIfAbsent方法 存储到map中 返回null 代表成功
                if (map.putIfAbsent(COUNT_KEY, 1) == null) {
                    break;
                }
            } else if (map.replace(COUNT_KEY, count, count+1)) {
                break;
            }
        }
        map.putIfAbsent("2", 2);
    }

    public static void cslm() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
//        map.put()

    }


}

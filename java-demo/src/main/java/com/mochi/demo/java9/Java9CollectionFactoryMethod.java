package com.mochi.demo.java9;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合类工厂方法
 * 生成的集合是不可修改的
 */
public class Java9CollectionFactoryMethod {

    public static void main(String[] args) {
        // Map.of 方法最多支持10个键值对的直接创建 ( 超过10个可以用Map.ofEntries()方法 )
        Map<String, Integer> map = Map.of("A", 1, "B", 2, "C", 3);
        Set<String> set = Set.of("A", "B", "C");
        List<String> list = List.of("A", "B", "C");

    }

}

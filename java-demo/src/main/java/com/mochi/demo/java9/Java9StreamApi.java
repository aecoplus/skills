package com.mochi.demo.java9;

import java.util.Optional;
import java.util.stream.Stream;

public class Java9StreamApi {

    public static void main(String[] args) {
        stream();

        optional();

    }

    /**
     * Stream类新方法
     * of() iterator().limit() takeWhile() dropWhile()
     */
    private static void stream() {
        // Stream.of方法直接生成流
        Stream.of("A", "B", "C")
                .filter(s -> "B".equals(s))
                .distinct()
                .forEach(System.out::println);

        // 接受可控的参数 (不报错)
        Stream.ofNullable(null)
                .filter(s -> s.equals("B"))
                .forEach(System.out::println);

        // Stream.iterate 无限循环 使用limit截断
        Stream.iterate(0, i -> i + 1)
                .limit(7)
                .forEach(System.out::println);

        // takeWhile 取 i<7
        Stream.iterate(0, i -> i + 1)
                .takeWhile(i -> i < 7)
                .forEach(System.out::println);

        // dropWhile <7的都不要
        Stream.iterate(0, i -> i<  20, i -> i + 1)
                .dropWhile(i -> i < 7)
                .forEach(System.out::println);

        // Stream.iterate 有断言(Predicate)
        Stream.iterate(0, i -> i < 7, i -> i + 1)
                .forEach(System.out::println);
    }

    /**
     * Optional类新方法:
     * ifPresentOrElse() or()
     */
    private static void optional() {
        // Optional ifPresentOrElse
        Optional.ofNullable(null).ifPresentOrElse((a) -> {
            System.out.println("元素不为空 值为：" + a);
        }, () -> {
            System.out.println("元素为空");
        });

        // Optional or(Supplier supplier)
        Optional<Object> optional = Optional.ofNullable(null).or(() -> Optional.of("AAA"));
    }
}

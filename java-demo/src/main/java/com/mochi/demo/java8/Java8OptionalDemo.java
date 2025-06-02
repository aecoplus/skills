package com.mochi.demo.java8;

import java.util.Optional;

public class Java8OptionalDemo {

    public static void main(String[] args) {
        String a = "1";

        Optional.ofNullable(a).ifPresent(System.out::println);

    }

}

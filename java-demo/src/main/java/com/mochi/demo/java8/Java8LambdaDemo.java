package com.mochi.demo.java8;

import java.util.Arrays;

public class Java8LambdaDemo {

    public static void main(String[] args) {
        Integer[] arrays = {1, 3, 5, 2, 1, 3};
        Arrays.sort(arrays, Integer::compareTo);

        for (Integer array : arrays) {
            System.out.println(array);
        }
    }

}

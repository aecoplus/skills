package com.mochi.demo.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataUtil {

    public static List<Food> genData() {
        List<Food> list = new ArrayList<>(5);
        Food f1 = new Food("馒头", 100);
        Food f2 = new Food("鸡腿", 180);
        Food f3 = new Food("米饭", 190);
        Food f4 = new Food("牛奶", 160);
        Food f5 = new Food("mochi", 160);
        list.add(f1);
        list.add(f2);
        list.add(f3);
        list.add(f4);
        list.add(f5);
        return list;
    }

    public static List<Integer> genNumbers() {
        return Arrays.asList(1,2,3,4,5);
    }
}

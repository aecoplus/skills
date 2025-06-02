package com.mochi.demo.java11;

import java.util.stream.Collectors;

public class Java11String {

    public static void main(String[] args) {
        String str = "A";
        str.isBlank(); // 是否为空 或者仅包含空格
        str.isEmpty(); // 是否为空

        str.lines().collect(Collectors.toList());

        str.strip(); // 去除收尾空格

        str.stripLeading(); // 去除首部空格

        str.stripTrailing(); // 去除尾部空格
    }
}

package com.mochi.demo.java10;

public class Java10Var {

    public static void main(String[] args) {
        varTest();
    }

    private static void varTest() {
        var a = 1;
        System.out.println(a);
        var b = "b";
        System.out.println(b.getClass());
    }
}

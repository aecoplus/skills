package com.mochi.demo.java12to16;

public class SwitchDemo {

    public static void main(String[] args) {
        String str = "B";

        String trans = switch (str) {
            case "A" -> str.repeat(3);
            case "B" -> {
                System.out.println("case B");
                yield str.repeat(2);
            }
            default -> {

                yield null;
            }
        };

        System.out.println(trans);
    }

}

package com.mochi.demo.java9;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Java9Ohters {

    public static void main(String[] args) {
        try {
            tryWithResource();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * try-with-resource 不需要再完整的声明一个变量了 可以将现有的变量丢进去
     */
    private static void tryWithResource() throws IOException {
        InputStream inputStream = Files.newInputStream(Path.of("D:\\mochi\\code\\a.txt"));

        int tryBeforeAvailable = inputStream.available();
        System.out.println("tryBeforeAvailable:" + tryBeforeAvailable);

        try (inputStream) {
            for (int i = 0; i < 100; i++) {
                System.out.println((char) inputStream.read());
            }
        }

        // 会报错 因为获取不到流(已被关闭)
        int tryAfterAvailable = inputStream.available();
        System.out.println("tryAfterAvailable:" + tryAfterAvailable);
    }

    private static void nmnbl() {
        Runnable runnable  = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        };
    }
}

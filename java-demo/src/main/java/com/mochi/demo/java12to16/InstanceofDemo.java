package com.mochi.demo.java12to16;

public class InstanceofDemo {

    private String name;

    public InstanceofDemo(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        // instanceof 直接跟一个类+对象
        if (obj instanceof InstanceofDemo demo) {
            return this.name.equals(demo.name);
        }
        return false;
    }

}

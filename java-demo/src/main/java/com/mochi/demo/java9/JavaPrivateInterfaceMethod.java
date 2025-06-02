package com.mochi.demo.java9;

/**
 * 接口的私有方法：
 * 允许在接口中定义私有方法，从而提高代码的复用性和可读性
 * 私有方法通常用于在接口内部实现共享的辅助功能，而不会暴露给接口的实现类或外部使用者。
 */
public interface JavaPrivateInterfaceMethod {

    int add(int a, int b);

    int sub(int a, int b);

    int mul(int a, int b);

    default int div(int a, int b) {
        return divHelper(a, b);
    }

    /**
     * 接口的私有化方法
     *
     * @param dividend
     * @param divisor
     * @return
     */
    private int divHelper(int dividend, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("divisor can not be zero");
        }
        return dividend / divisor;
    }

}

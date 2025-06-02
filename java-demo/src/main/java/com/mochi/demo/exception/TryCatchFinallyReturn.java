package com.mochi.demo.exception;

public class TryCatchFinallyReturn {

    public static void main(String[] args) {

        /**
         * finally中无return：
         *      当try块中或者catch块中遇到return语句时，
         *      先执行完finally里面的代码后，再执行try 或者 catch中的return语句。
         */

        /**
         * 当finally里面有return语句：
         *      ①无异常时，走完try里面的代码，return也会走，
         *      然后执行finally里面的代码，最后finally里面的return会覆盖掉try之前return的结果;
         *      ②有异常时，走catch里面的代码，同样继续执行了catch里面的return，
         *      最后走finally里面的代码，finally里面的rerturn最终会覆盖catch的return;
         *  无论是否异常，finally不会影响try、catch里面的return语句，
         *  只是当finally里面的return会最后覆盖掉前面return的结果！！
         *
         *  finally中改变变量值不会影响catch中return中返回的值
         */

        // finally一般不要写return，系统认为会是一个warning
        // finally里面的return不会影响try、catch里面的return，最后只是覆盖掉前面的return而已;
    }

    private static int test1() {
        int i = 0;
        try {
            i++; // 1
            i = i / 0;
            return i++;
        } catch (Exception e) {
            i++; // 2
            return i++; // 3
        } finally {
            return ++i; // 4 finally块里面的return语句覆盖掉了前面的
        }
    }

    private static int test2() {
        int i = 0;
        try {
            i++; // 1
            i = i / 0;
            return i++;
        } catch (Exception e) {
            i++; // 2
            return i=7; // 7
        } finally {
            return ++i; // 8
        }
    }

    private static int test3() {
        int a = 10;
        try {
            System.out.println(a / 0);
            a = 20;
        } catch (ArithmeticException e) {
            a = 30;
            return a; // 最终返回30
            /*
             * return a 在程序执行到这一步的时候，这里不是return a 而是 return 30；这个返回路径就形成了
             * 但是呢，它发现后面还有finally，所以继续执行finally的内容，给a赋值40，但是finally并没有return；
             * 所以执行完finally后再次回到以前的路径,继续走return 30，形成返回路径之后，这里的a就不是a变量了，而是常量30
             */
        } finally {
            a = 40;
        }
        return a;
    }

    public static int test4() {
        int a = 10;
        try {
            System.out.println(a / 0);
            a = 20;
        } catch (ArithmeticException e) {
            a = 30;
            return a;
        } finally {
            a = 40;
            // 如果这样，就又重新形成了一条返回路径，由于只能通过1个return返回，所以这里直接返回40
            return a; // 最终返回40
        }
    }

}

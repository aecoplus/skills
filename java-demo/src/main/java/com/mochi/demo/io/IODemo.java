package com.mochi.demo.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class IODemo {

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        // close()方法是由java.lang包中的AutoCloseable接口指定的，java.io包中的Closeable接口继承了AutoCloseable接口。所有的流类都实现了这两个接口

        // 自动资源管理，该特性以try语句的扩展板为基础 eg.
        try(FileInputStream fin = new FileInputStream(args[0]);
            FileOutputStream fout = new FileOutputStream(args[1]);)//try语句块管理多个资源
        {int i = fin.read();}//fin变量仅仅局限于try代码块中，当离开try代码块时，会隐式的调用close方法以关闭与fin关联的流
        // try语句中声明的资源被隐式的声明为final，意味着在创建资源变量后，不能将其他资源赋给该变量；资源的作用域局限于带资源的try语句块中

        // 静态导入，直接通过名称引用静态成员，而不需要使用类名进行限定
        // import static java.lang.Math.sqrt; 或者 import static java.lang.Math.*;

        // 紧凑API配置文件
        // JDK8将API库的子集组织成所谓的紧凑配置文件，优点：用不到完整库的应用程序不需要下载整个库
        // 编译程序时，-profile选项确定程序是否只使用了紧凑配置文件中定义的API
        // javac -profile compact1 Test.java

    }

}

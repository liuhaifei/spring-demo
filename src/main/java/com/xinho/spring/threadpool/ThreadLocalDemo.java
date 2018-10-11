package com.xinho.spring.threadpool;

/**
 * @ClassName ThreadLocalDemo
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/7 18:02
 * @Version 1.0
 **/
public class ThreadLocalDemo {
    ThreadLocal<String> stringLocal=new ThreadLocal<>();
    ThreadLocal<Long> longLocal=new ThreadLocal<>();

    public void set(){
        stringLocal.set(Thread.currentThread().getName());
        longLocal.set(Thread.currentThread().getId());
    }
    public long getLong(){
        return longLocal.get();
    }
    public String getString(){
        return stringLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalDemo demo=new ThreadLocalDemo();
        demo.set();
        System.out.println(demo.getString()+"-----"+demo.getLong());

        Thread thread=new Thread(()->{
            demo.set();
            System.out.println(demo.getString()+"-----"+demo.getLong());
        },"lhf");
        thread.start();

        thread.join();

        System.out.println(demo.getString()+"-----"+demo.getLong());
    }
}

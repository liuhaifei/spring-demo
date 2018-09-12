package com.xinho.spring.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ExecuteCaugth
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/12 14:51
 * @Version 1.0
 **/
public class ExecuteCaugth {
    private static ExecutorService executorService= Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        try {
            Thread thread=new Thread(new Task());
            executorService.execute(thread);
            executorService.shutdown();

        }catch (Exception e){
            System.out.println("Exception:"+e.getMessage());
        }

    }

    static class Task implements Runnable{

        @Override
        public void run() {
            Thread.currentThread().setUncaughtExceptionHandler(new WithCaugthTread.ExceptionHandle());
            System.out.println(3/2);
            System.out.println(3/0);
            System.out.println(3/1);
        }
    }
}

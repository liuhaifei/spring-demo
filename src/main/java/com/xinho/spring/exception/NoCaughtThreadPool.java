package com.xinho.spring.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NoCaughtThreadPool
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/12 14:28
 * @Version 1.0
 **/
public class NoCaughtThreadPool {

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
            System.out.println(3/2);
            System.out.println(3/0);
            System.out.println(3/1);
        }
    }
}

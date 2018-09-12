package com.xinho.spring.exception;

import java.io.IOException;

/**
 * @ClassName NoCaughtThread
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/12 14:22
 * @Version 1.0
 **/
public class NoCaughtThread {

    public static void main(String[] args) {
        try {
            Thread thread=new Thread(new Task());
            thread.start();
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
//            throw new RuntimeException("程序异常");
        }
    }
}

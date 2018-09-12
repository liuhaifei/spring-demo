package com.xinho.spring.exception;

/**
 * @ClassName WithCaugthTread
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/12 14:40
 * @Version 1.0
 **/
public class WithCaugthTread {

    public static void main(String[] args) {
        Thread thread=new Thread(new NoCaughtThread.Task());
        thread.setUncaughtExceptionHandler(new ExceptionHandle());
        thread.start();

    }
    static class ExceptionHandle implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Exception:"+e.getMessage());
        }
    }
}

package com.xinho.spring.exception;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName SubmitCaught
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/12 14:56
 * @Version 1.0
 **/
public class SubmitCaught {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Future future=exec.submit(new ExecuteCaugth.Task());
        try {
            future.get();
        } catch (InterruptedException e) {
            System.out.println("==Exception: "+e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("==Exception: "+e.getMessage());
        }
        exec.shutdown();
    }
}

package com.xinho.spring.lock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName CountDownLatchDemo
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/28 14:10
 * @Version 1.0
 **/
public class CountDownLatchDemo {

    public static void main(String[] args) {
        int threadNum = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        for (int i = 0; i < threadNum; i++) {
            final int finalI = i + 1;
            new Thread(() -> {
                System.out.println("thread " + finalI + " start");
                Random random = new Random();
                try {
                    Thread.sleep(random.nextInt(10000) + 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread " + finalI + " finish");

                countDownLatch.countDown();
            }).start();
        }

        try {
            System.out.println("111");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadNum + " thread finish");


    }
}

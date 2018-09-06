package com.xinho.spring.lock;

import java.util.concurrent.locks.Lock;

/**
 * @ClassName WaitEvent
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/28 11:05
 * @Version 1.0
 **/
public class WaitEvent extends Thread {

    private Lock lock;

    public WaitEvent(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

            System.out.println("线程进入、加锁----");
            lock.lock();
            System.out.println("执行完成");

    }
}

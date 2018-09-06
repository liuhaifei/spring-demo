package com.xinho.spring.lock;

import java.util.concurrent.locks.Lock;

/**
 * @ClassName NotifyEvent
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/28 11:24
 * @Version 1.0
 **/
public class NotifyEvent extends Thread {

    private Lock lock;

    public NotifyEvent(Lock lock) {
        this.lock = lock;
    }


    @Override
    public void run() {

        System.out.println("线程"+Thread.currentThread().getName()+"释放锁----");
        lock.unlock();
        System.out.println("释放锁完成");

    }
}

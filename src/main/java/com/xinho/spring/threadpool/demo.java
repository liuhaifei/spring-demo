package com.xinho.spring.threadpool;

import com.xinho.spring.lock.NotifyEvent;
import com.xinho.spring.lock.WaitEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName demo
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/22 10:47
 * @Version 1.0
 **/
public class demo {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    public static void main(String[] args) {

//        System.out.println(COUNT_BITS);
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(CAPACITY);
//        System.out.println(RUNNING);
//        System.out.println(SHUTDOWN);
//        System.out.println(STOP);
//        System.out.println(TIDYING);
//        System.out.println(TERMINATED);

        Lock lock=new ReentrantLock();
        WaitEvent wait=new WaitEvent(lock);
        NotifyEvent notity=new NotifyEvent(lock);
        wait.start();
        try {
            wait.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notity.start();

    }
}

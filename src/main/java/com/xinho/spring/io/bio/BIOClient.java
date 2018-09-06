package com.xinho.spring.io.bio;

import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName BIOClient
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 11:23
 * @Version 1.0
 **/
public class BIOClient {
    public static void main(String[] args) {
        int count=100;
        final CountDownLatch latch=new CountDownLatch(count);

        for(int i=0;i<count;i++){
            new Thread(()->{
                try {
                    latch.await();
                    System.out.println("2222");
                    Socket client=new Socket("localhost",8080);
                    OutputStream os=client.getOutputStream();
                    String name= UUID.randomUUID().toString();

                    os.write(name.getBytes());
                    os.flush();
                    os.close();
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println("1111");
            latch.countDown();

        }
    }

}

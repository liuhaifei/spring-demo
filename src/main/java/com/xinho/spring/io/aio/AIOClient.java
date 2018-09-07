package com.xinho.spring.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName AIOClient
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/7 14:32
 * @Version 1.0
 **/
public class AIOClient {
    private final AsynchronousSocketChannel client;

    public AIOClient() throws IOException {
        this.client = AsynchronousSocketChannel.open();
    }
    public void connect(String host,int port) throws InterruptedException {
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {

            @Override
            public void completed(Void result, Object attachment) {
                try {
                    client.write(ByteBuffer.wrap(("这是一条测试数据"+System.currentTimeMillis()).getBytes())).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        //只读
        final ByteBuffer buffer=ByteBuffer.allocate(1024);
        client.read(buffer, null, new CompletionHandler<Integer, Object>() {

            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("获取反馈数据:"+new String(buffer.array()));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws InterruptedException {
        int count=10;
        final CountDownLatch latch=new CountDownLatch(count);
        for (int i=0;i<count;i++){
            new Thread(()->{
                try {
                    latch.await();
                    try {
                        new AIOClient().connect("localhost",8080);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            latch.countDown();
        }
        Thread.sleep(1000 * 60 * 10);
    }
}

package com.xinho.spring.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ClassName AIOServer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/7 13:56
 * @Version 1.0
 **/
public class AIOServer {
    private final int port;

    public AIOServer(int port) {
        this.port = port;
    }
    private void listen(){

        //线程池 体现异步
        ExecutorService executorService= Executors.newCachedThreadPool();

        try {
            //给线程池初始化一个线程
            AsynchronousChannelGroup threadGroup=AsynchronousChannelGroup.withCachedThreadPool(executorService,1);
            //AIO AsynchronousServerSocket
            //NIO ServerSocketChannel
            //BIO ServerSocket

            //同样 先修路
            final AsynchronousServerSocketChannel server=AsynchronousServerSocketChannel.open(threadGroup);

            //打开高速公路的关卡
            server.bind(new InetSocketAddress(port));
            System.out.println("服务已经启动，监听端口:"+port);

            final Map<String,Integer> count=new ConcurrentHashMap<>();
            count.put("count",0);

            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

                final ByteBuffer buffer=ByteBuffer.allocate(1024);
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    count.put("count",count.get("count")+1);
                    System.out.println(count.get("count"));


                    try {
                        buffer.clear();
                        result.read(buffer).get();
                        buffer.flip();
                        result.write(buffer);
                        buffer.flip();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            result.close();
                            server.accept(null, this);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("io操作失败");
                }
            });

            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AIOServer(8080).listen();
    }
}

package com.xinho.spring.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName BIOServer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 11:29
 * @Version 1.0
 **/
public class BIOServer {

    private ServerSocket serverSocket;

    public BIOServer(int port) {
        try {
            this.serverSocket=new ServerSocket(port);
            System.out.println("BIO服务已经启用，监听端口是："+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listener() throws IOException{
        while (true){
            //如果一直没有客户端连接的话 这里一直会阻塞
            Socket socket=serverSocket.accept();
            InputStream is=socket.getInputStream();
            byte[] buff=new byte[1024];
            int len=is.read(buff);
            //只要一直有数据写入，len就会大于0
            if(len>0){
                String msg=new String(buff,0,len);
                System.out.println(Thread.currentThread().getName()+"---->"+msg);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new BIOServer(8080).listener();
    }
}

package com.xinho.spring.io.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName NIOServer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/6 10:51
 * @Version 1.0
 **/
public class NIOServer {

    private int port=8080;
    private Charset charset=Charset.forName("UTF-8");
    private static Set<String> users=new HashSet<>();

    private static String USER_EXIST="提示";
    private static String USER_CONTENT_SPLIT="#@#";
    private Selector selector=null;

    public NIOServer(int port) throws IOException {
        this.port = port;
        //把通道打开(高速公路)
        ServerSocketChannel server=ServerSocketChannel.open();
        //设置关卡
        server.bind(new InetSocketAddress(this.port));
        //是指为非阻塞
        server.configureBlocking(false);

        //排队大厅，排队开始工作
        selector=Selector.open();
        //告诉大厅工作人员，可以开始接待了
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经启动，监听的端口是:"+this.port);
    }

    public void listener() throws IOException {
        while (true){
            //在轮询，服务大厅中，到底有多少个人在排队
            int wait=selector.select();
            if(wait==0)continue;
            //取号，默认分配号码（排队拿号）
            Set<SelectionKey> keys=selector.selectedKeys();
            Iterator<SelectionKey> iterator=keys.iterator();
            while (iterator.hasNext()){
                SelectionKey key=iterator.next();
                //处理一个号码，号码就消除
                //过号不候
                iterator.remove();
                //处理逻辑
                process(key);
            }
        }
    }

    private void process(SelectionKey key) throws IOException {
        //判断客户端确定已经进入服务大厅并且已经可以实现交互了
        if(key.isAcceptable()){
            ServerSocketChannel server=(ServerSocketChannel)key.channel();
            //非阻塞模式
            server.configureBlocking(false);
            SocketChannel channel=server.accept();
            channel.configureBlocking(false);
            // 注册选择器，并设置为读取模式，收到一个请求，然后起一个SocketChannel
            //并注册到selector上，之后这个连接的数据，就由这个socketCahnnel处理
            channel.register(selector,SelectionKey.OP_READ);
            //将此对应的channel设置为准备接受其他客户端请求
            key.interestOps(SelectionKey.OP_ACCEPT);
            channel.write(charset.encode("请输入你的昵称："));
        }
        //处理来自客户端的数据读取请求
        if(key.isReadable()){
            //返回该SelectionKey对应的Chanel，其中有数据需要读取
            SocketChannel channel=(SocketChannel)key.channel();
            //往缓冲区读取数据
            ByteBuffer buffer=ByteBuffer.allocate(1024);
            StringBuilder sb=new StringBuilder();
           try {
               while (channel.read(buffer)>0){
                   buffer.flip();
                   sb.append(charset.decode(buffer));
               }
               //将此对应的channle设置为下一次准备接受数据
               key.interestOps(SelectionKey.OP_READ);
           }catch (IOException e){
               key.cancel();
               if(key.channel()!=null){
                   key.channel().close();
               }
           }
           if(sb.length()>0){
               String[] arrayContent=sb.toString().split(USER_CONTENT_SPLIT);
               //注册用户
               if(arrayContent!=null&& arrayContent.length==1){
                   String name=arrayContent[0];
                   if(users.contains(name)){
                       channel.write(charset.encode(USER_EXIST));
                   }else {
                       users.add(name);
                       int onlineCout=onlineCount();
                       String message = "欢迎 " + name + " 进入聊天室! 当前在线人数:" + onlineCout;
                        broadCast(null,message);
                   }
               }else if(arrayContent!=null && arrayContent.length>1){
                   //注册完了 发送消息
                   String name=arrayContent[0];
                   String message=sb.substring(name.length()+USER_CONTENT_SPLIT.length());
                   message=name+"说"+message;
                   if(users.contains(name)){
                       //不回发给发送此内容的客户端
                       broadCast(channel,message);
                   }
               }
           }
        }
    }

    //TODO 要是能检测下线，就不用这么统计了
    public int onlineCount() {
        int res = 0;
        for(SelectionKey key : selector.keys()){
            Channel target = key.channel();

            if(target instanceof SocketChannel){
                res++;
            }
        }
        return res;
    }
    public void broadCast(SocketChannel client, String content) throws IOException {
        //广播数据到所有的SocketChannel中
        for(SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            //如果client不为空，不回发给发送此内容的客户端
            if(targetchannel instanceof SocketChannel && targetchannel != client) {
                SocketChannel target = (SocketChannel)targetchannel;
                target.write(charset.encode(content));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NIOServer(8080).listener();
    }
}

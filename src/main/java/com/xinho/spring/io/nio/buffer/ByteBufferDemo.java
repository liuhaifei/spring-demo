package com.xinho.spring.io.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @ClassName ByteBufferDemo
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 16:34
 * @Version 1.0
 **/
public class ByteBufferDemo {
    public static void main(String[] args) {
        //分配指定的缓冲区
        ByteBuffer buffer=ByteBuffer.allocate(10);

        //包装一个现有的数组
        byte[] array=new byte[10];
        ByteBuffer buffer1=ByteBuffer.wrap(array);
    }
}

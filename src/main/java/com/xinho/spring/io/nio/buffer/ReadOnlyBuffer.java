package com.xinho.spring.io.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @ClassName ReadOnlyBuffer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 14:27
 * @Version 1.0
 **/
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(10);
        //缓冲区中的数据0-9
        System.err.println("缓冲区容量"+buffer.capacity());
        for(int i=0;i<buffer.capacity();++i){
            buffer.put((byte)i);
        }
        //创建只读缓冲区
        ByteBuffer readOnly=buffer.asReadOnlyBuffer();
        //改变原缓冲区的内容
        for(int i=0;i<buffer.capacity();++i){
            byte b=buffer.get(i);
            b*=10;
            buffer.put(i,b);
        }
        readOnly.position(0);
        readOnly.limit(buffer.capacity());

        //只读缓冲区的内容也随之改变
        while (readOnly.remaining()>0){
            System.out.println(readOnly.get());
        }
    }
}

package com.xinho.spring.io.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @ClassName BufferSlice
 * @Description 创建子缓冲区
 * @Author 刘海飞
 * @Date 2018/9/5 16:43
 * @Version 1.0
 **/
public class BufferSlice {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(10);

        //缓冲区中的数据0-9
        for (int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }

        //创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice=buffer.slice();

        //改变子缓冲区的内容
        for (int i=0;i<slice.capacity();i++){
             byte b=slice.get();
            b*=10;
            slice.put(i,b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining()>0){
            System.out.println(buffer.get());
        }
    }
}


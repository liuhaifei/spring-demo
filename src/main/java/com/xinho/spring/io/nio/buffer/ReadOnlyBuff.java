package com.xinho.spring.io.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @ClassName ReadOnlyBuff
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 17:42
 * @Version 1.0
 **/
public class ReadOnlyBuff {

    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(10);
        for (int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }
        //创建只读缓冲区
        ByteBuffer readOnly=buffer.asReadOnlyBuffer();

        //改变原缓冲区的内容
        for (int i=0;i<buffer.capacity();i++){
            byte b=buffer.get(i);
            b*=10;
            buffer.put(i,b);
        }


        readOnly.position(0);
        readOnly.limit(buffer.capacity());

        while (readOnly.hasRemaining()){
            System.out.println(readOnly.get());
        }
    }
}

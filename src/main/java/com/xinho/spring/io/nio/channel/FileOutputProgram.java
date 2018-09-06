package com.xinho.spring.io.nio.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName FileOutputProgram
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/6 10:00
 * @Version 1.0
 **/
public class FileOutputProgram {
    private static final byte message [] ={12,34,45,56,67,86,43};

    public static void main(String[] args) throws IOException {
        FileOutputStream fos=new FileOutputStream("E:\\test1.txt");
        FileChannel fc=fos.getChannel();
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        for (int i=0;i<message.length;i++){
            buffer.put(message[i]);
        }
        buffer.flip();
        fc.write(buffer);
        fos.close();

    }
}

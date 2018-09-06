package com.xinho.spring.io.nio.buffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName DirectBuffer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 17:55
 * @Version 1.0
 **/
public class DirectBuffer {
    public static void main(String[] args) throws IOException {
        String infile="E:\\test.txt";
        FileInputStream fis=new FileInputStream(infile);
        FileChannel fc=fis.getChannel();

        String outfile=String.format("E:\\testcopy.txt");
        FileOutputStream fos=new FileOutputStream(outfile);
        FileChannel fcout=fos.getChannel();

        //使用allocateDirect
        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
        while (true){
            buffer.clear();
            int r=fc.read(buffer);
            if(r==-1){
                break;
            }
            buffer.flip();
            fcout.write(buffer);
        }

    }
}

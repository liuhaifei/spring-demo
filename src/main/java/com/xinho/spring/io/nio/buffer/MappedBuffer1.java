package com.xinho.spring.io.nio.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName MappedBuffer1
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 14:19
 * @Version 1.0
 **/
public class MappedBuffer1 {
    static private final int start=0;
    static private final int size=1024;

    public static void main(String[] args) throws IOException {
        RandomAccessFile raf=new RandomAccessFile("E:\\test.txt","rw");
        FileChannel fc=raf.getChannel();

        //把缓冲区跟文件系统进行一个映射关联
        //只要操作缓冲区里面的内容，文件内容也会跟着改变
        MappedByteBuffer mbb=fc.map(FileChannel.MapMode.READ_WRITE,start,size);
        mbb.put(0,(byte)97);
        mbb.put(1023,(byte)122);

        raf.close();
    }
}

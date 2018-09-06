package com.xinho.spring.io.nio.buffer;

import java.nio.IntBuffer;

/**
 * @ClassName TestIntBuffer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/9/5 15:50
 * @Version 1.0
 **/
public class TestIntBuffer {
    public static void main(String[] args) {
        //新缓冲区的当前位置为0，其界限limit为其容量capacity，
        //其底层实现了一个数组，数组偏移量offset为0
        IntBuffer intBuffer=IntBuffer.allocate(8);

        for (int i=0;i<intBuffer.capacity();i++){
            int j=2*(i+1);
            intBuffer.put(j);
        }

        //重设次缓冲区，将限制设置为当前位置，然后将当前位置设置为0
        intBuffer.flip();
        //查看当前位置和限制位置之间是否有元素
        while (intBuffer.hasRemaining()){
            //读取次缓存区当前位置的整数，然后当前位置递增
            int j=intBuffer.get();
            System.out.println(j+"\n");
        }

    }
}

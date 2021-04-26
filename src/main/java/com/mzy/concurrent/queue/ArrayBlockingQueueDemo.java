package com.mzy.concurrent.queue;

import com.mzy.concurrent.config.ThreadPoolConfig;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;

/**
 * @author Jack Miao
 * @date 2021/4/20 9:47
 * @desc
 */
public class ArrayBlockingQueueDemo {
    /**
    * ConcurrentLinkedDeque:
    *    双向链表的无界阻塞队列,支持 LILO 和 LIFO 两种模式*
    * ConcurrentLinkedDeque:
    *    单向列表的无界阻塞队列, LILO
    */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = ThreadPoolConfig.getThreadPool();

        /*ConcurrentLinkedDeque linkedDeque = new ConcurrentLinkedDeque();
        linkedDeque.add(1);
        linkedDeque.add(2);
        linkedDeque.addFirst(3);
        System.out.println(linkedDeque);*/


        /*ArrayBlockingQueue demo = new ArrayBlockingQueue(2);
        *//**
        * add(),列已满,抛出异常
        *//*
        *//*demo.add("2");
        demo.add("3");*//*

        *//**
         * put(),列已满,线程阻塞等待
         *//*
        demo.put(1);
        demo.put(2);
        *//**
         * peek(), 返回下一个要操作的 E e
         *//*
        demo.peek();
        *//**
         * poll(), 删除并弹出
         *//*
        demo.poll();*/

    }
}

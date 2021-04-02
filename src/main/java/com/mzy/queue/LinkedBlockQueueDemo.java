package com.mzy.queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jack Miao
 * @date 2021/2/25 16:11
 * @desc
 */
public class LinkedBlockQueueDemo {
    /**
     * 有界Linked队列,Integer.MAX_VALUE
     *
     */
    public static void main(String[] args) throws InterruptedException {

        LinkedBlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
        blockingDeque.add(1);
        blockingDeque.add(3);
        blockingDeque.add(2);

       /* System.out.println(blockingDeque.getFirst());
        System.out.println(blockingDeque.getLast());
        //降序迭代器 从链表last到first
        Iterator<Integer> integerIterator = blockingDeque.descendingIterator();
        while(integerIterator.hasNext()){
            System.out.println(integerIterator.next());
        }*/

        //等待指定时间获取
        Integer poll = blockingDeque.poll(10, TimeUnit.SECONDS);
        System.out.println(poll);
        int i = blockingDeque.remainingCapacity();
        System.out.println(i);

    }
}

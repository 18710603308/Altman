package com.mzy.queue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author Jack Miao
 * @date 2021/2/25 15:11
 * @desc
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(5, false);
        //非阻塞添加,队列满抛异常
        queue.add("1");
        queue.poll();
        queue.add("2");
        queue.clear();
        //添加成功返回true,失败返回false
        boolean offer = queue.offer("3");
        System.out.println(offer);
        //返回队列头元素
        System.out.println(queue.peek());
        //删除指定元素
        System.out.println(queue.remove("1"));
        queue.offer("4");
        queue.offer("3");
        System.out.println("reaming: " + queue.remainingCapacity());
        ArrayList<String> strings = new ArrayList<>();
        //将queue中的所有元素移动到指定collection
        queue.drainTo(strings);
        System.out.println(queue);
        System.out.println(strings);

    }
}

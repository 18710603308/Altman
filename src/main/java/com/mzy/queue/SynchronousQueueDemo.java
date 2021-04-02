package com.mzy.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * @Author Jack Miao
 * @date 2021/2/25 16:46
 * @desc
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue();

        new Thread(() ->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {

            }
        }).start();


        new Thread(() -> {
            queue.offer(1);
        }).start();

        new Thread(() -> {
            try {
                queue.put(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}

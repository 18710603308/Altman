package com.mzy.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author Jack Miao
 * @date 2021/2/25 17:17
 * @desc 非阻塞的无界安全队列
 */
public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) {

        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        Asynopertion asyn = new Asynopertion();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                asyn.add(queue);
            }).start();
        }

        System.out.println(queue.poll());
    }

}

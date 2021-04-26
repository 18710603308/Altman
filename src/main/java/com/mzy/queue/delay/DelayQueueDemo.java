package com.mzy.queue.delay;

import java.util.concurrent.DelayQueue;

/**
 * @Author Jack Miao
 * @date 2021/2/25 16:53
 * @desc
 */
public class DelayQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        DelayQueue delayQueue = new DelayQueue();
        //延迟指定时间执行
        DelayChiled delayChiled = new DelayChiled(3000 + System.currentTimeMillis());

        delayQueue.add(delayChiled);
        //System.out.println(delayQueue.take());
    }
}

package com.mzy.queue;

import org.springframework.scheduling.annotation.Async;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Jack Miao
 * @date 2021/2/25 17:22
 * @desc
 */
public class Asynopertion {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    @Async
    public void add(Queue queue){
        queue.add(atomicInteger.incrementAndGet());
    }
}

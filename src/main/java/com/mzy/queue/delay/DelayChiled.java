package com.mzy.queue.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jack Miao
 * @date 2021/2/25 17:01
 * @desc
 */
public class DelayChiled implements Delayed {

    private long time;

    public DelayChiled(long time) {
        this.time = time;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return this.time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return this.time - System.currentTimeMillis() > 0 ? 0 : 1;
    }
}

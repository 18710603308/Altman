package com.mzy.concurrent.count_down_latch;

import com.mzy.concurrent.config.ThreadPoolConfig;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author Jack Miao
 * @date 2021/4/22 10:15
 * @desc 线程同步控制器
 */
public class CountDownLatchDemo {

    /**
    * CountDownLatch:
     *  线程同步控制器,
     *  1. 初始化时设置线程数量. CountDownLatch count = new CountDownLatch(10);
     *  2. 执行完一个线程, countDown  count.countDown()
     *  3. count.await(); 等待计数器归零,继续向下执行.
     *      await(时间数量, 时间单位) 设置超时时间,等待指定时间,超时则释放
    */
    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = ThreadPoolConfig.getThreadPool();
        CountDownLatch count = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(()->{
                if(finalI %2 == 0){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread());
            });
            count.countDown();
        }
        count.await();
        System.out.println("执行完毕: " + Thread.currentThread());
    }
}

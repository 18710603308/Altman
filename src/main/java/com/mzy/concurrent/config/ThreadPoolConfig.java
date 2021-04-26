package com.mzy.concurrent.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack Miao
 * @date 2021/4/15 16:40
 * @desc
 */
public class ThreadPoolConfig {


    public static ExecutorService getThreadPool(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}

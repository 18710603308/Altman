/*
package com.mzy.mdc_log;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

*/
/**
 * @Author Jack Miao
 * @date 2021/2/26 11:12
 * @desc
 *//*

public class MdcDemo {
    private static ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));

    public static void main(String[] args) {

        */
/*ThreadLocal<Map<Object, Object>> threadLocal = new TransmittableThreadLocal();
        ThreadLocal<Map<Object, Object>> local = new ThreadLocal<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("traceId", "mzy");
        threadLocal.set(map);
        local.set(map);*//*


        ThreadLocal<Integer> local = new TransmittableThreadLocal<>();

        new Thread(() -> {
            String mainThreadName = "mainThread";
            local.set(1);
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(1), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(1), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(1), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), local.get()));
            });

            sleep();
            local.set(2);

            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), local.get()));
            });
            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), local.get()));
        }).start();


        sleep();
        local.set(5);
        new Thread(() -> {
            String twoThreamName = "twoName";
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", twoThreamName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", twoThreamName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", twoThreamName, Thread.currentThread().getName(), local.get()));
            });
            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), local.get()));

            sleep();
            local.set(6);
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", twoThreamName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", twoThreamName, Thread.currentThread().getName(), local.get()));
            });
            executorService.execute(() -> {
                sleep();
                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", twoThreamName, Thread.currentThread().getName(), local.get()));
            });
            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), local.get()));

            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), local.get()));
        }).start();



        //System.out.println(MDC.get("traceId"));
    }

    private static void sleep(){
        try {
            Thread.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
*/

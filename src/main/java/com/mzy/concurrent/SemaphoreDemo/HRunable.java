package com.mzy.concurrent.SemaphoreDemo;

/**
 * @author mzy
 * @date 2021/5/25 23:28
 */
public class HRunable implements Runnable {
    @Override
    public void run() {
        System.out.println("H");
    }
}

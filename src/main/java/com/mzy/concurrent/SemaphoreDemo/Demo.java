package com.mzy.concurrent.SemaphoreDemo;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

/**
 * @author mzy
 * @date 2021/5/25 23:30
 */
public class Demo {

    Semaphore o = new Semaphore(2);
    Semaphore h = new Semaphore(2);

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        h.acquire();
        releaseHydrogen.run();
        o.release();
        System.out.println("H:   "+h.availablePermits() + ":" + o.availablePermits());
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        o.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        h.release(2);
        System.out.println("O     " + h.availablePermits() + ":" + o.availablePermits());
    }

    public static void main(String[] args) throws InterruptedException {
        final Demo demo = new Demo();
        for (int i = 0; i < 5 ; i++) {

            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    demo.oxygen(new ORunable());
                }
            }).start();
        }
        for (int i = 0; i < 5 ; i++) {

            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    demo.hydrogen(new HRunable());
                }
            }).start();
        }
    }
}

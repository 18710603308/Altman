package com.mzy.thread;

/**
 * @author mzy
 * @date 2021/6/6 22:58
 */
public class DaemonDemo {

    public static void main(String[] args) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                                        } catch (InterruptedException e) {
                    System.out.println("----------------");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}

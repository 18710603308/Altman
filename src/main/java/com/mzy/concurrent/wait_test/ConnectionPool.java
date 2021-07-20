package com.mzy.concurrent.wait_test;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author mzy
 * @date 2021/7/14 23:01
 * 数据库连接池
 */
public class ConnectionPool {
    // 请求连接队列
    private final LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int initialSize) {
        if(initialSize > 0){
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    /**
     * 释放连接
     */
    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                // 按揭释放后需要通知,这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 获取连接
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            // 不设超时,一直等待
            if(mills <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                // 超时时间
                long feture = System.currentTimeMillis() + mills;
                // 等待时间
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = feture - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}

package com.mzy.concurrent.wait_test;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author mzy
 * @date 2021/7/14 22:53
 * 模拟连接驱动
 */
public class ConnectionDriver {

    static class ConnectionHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if("commit".equals(method.getName())){
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return null;
        }
    }
    // 创建一个Connection代理,在Commit时休眠100毫秒
    public static final Connection createConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class[]{ Connection.class}, new ConnectionHandler());
    }
}

package com.altman;

import com.altman.service.DemoService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author mzy
 * @date 2021/7/20 23:02
 */
public class DemoHandler implements InvocationHandler {

    private DemoService demoService;

    public DemoHandler() {
    }

    public DemoHandler(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Object invoke = method.invoke(demoService, args);
        return invoke;
    }
}

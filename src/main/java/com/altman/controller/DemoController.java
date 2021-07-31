package com.altman.controller;

import com.altman.ApplicationContextHelper;
import com.altman.DemoHandler;
import com.altman.service.DemoService;
import com.altman.service.impl.OneServiceImpl;
import com.altman.service.impl.TwoServiceImpl;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;


/**
 * @author mzy
 * @date 2021/7/20 23:04
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public void demo(@RequestParam("flag") String flag){
        final HashMap<String, Class> hashMap = new HashMap<>();
        hashMap.put("one", OneServiceImpl.class);
        hashMap.put("two", TwoServiceImpl.class);
        DemoService demoService;
        if("one".equals(flag)){
            final Class<DemoService> aClass = hashMap.get(flag);
            demoService = ApplicationContextHelper.getBean(aClass);
        }else{
            demoService = ApplicationContextHelper.getBean(TwoServiceImpl.class);
        }
        final InvocationHandler handler = new DemoHandler(demoService);
        DemoService service = (DemoService) Proxy.newProxyInstance(handler.getClass().getClassLoader(), demoService.getClass().getInterfaces(),
                handler);
        service.upload(flag, 1);
    }

}



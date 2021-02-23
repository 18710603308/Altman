package com.mzy.demo;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.mzy.event.ApplicationEventDemo;
import com.mzy.service.SendMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author Jack Miao
 * @date 2021/1/11 17:22
 * @desc
 */
@RestController
public class CacheController {

    static Logger logger = LoggerFactory.getLogger(CacheController.class);

    Cache<String, Object> manualCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();

    LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(key -> createExpensiveGraph(key));

    AsyncLoadingCache<String, Object> asyncLoadingCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            // Either: Build with a synchronous computation that is wrapped as asynchronous
            .buildAsync(key -> createExpensiveGraph(key));
    // Or: Build with a asynchronous computation that returns a future
    // .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));

    private CompletableFuture<Object> createExpensiveGraphAsync(String key, Executor executor) {
        CompletableFuture<Object> objectCompletableFuture = new CompletableFuture<>();
        return objectCompletableFuture;
    }

    private Object createExpensiveGraph(String key) {
        System.out.println("缓存不存在或过期，调用了createExpensiveGraph方法获取缓存key的值");
        if (key.equals("name")) {
            throw new RuntimeException("调用了该方法获取缓存key的值的时候出现异常");
        }
        return "1";
    }

    @Autowired
    private SendMessageServiceImpl sendMessageService;
    @ResponseBody
    @RequestMapping("/sendMessageDemo")
    public void sendMessageDemo(HttpServletRequest request, HttpServletResponse response) throws IOException, TimeoutException {
        sendMessageService.sendMsgToAmqp("mzy" + new Date().toString());
        //sendMeaasgeService.receivedMsgToAmqp();
    }

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/demoEvent")
    public void demoEvent(){
        applicationContext.publishEvent(new ApplicationEventDemo(this, "mzy"));
    }
}

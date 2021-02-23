package com.mzy.demo;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack Miao
 * @date 2021/1/11 15:00
 * @desc
 */
public class CaffeineCacheDemo {

    public static void main(String[] args) throws Exception{
        //manualLoading();
        //synchronizationLoading();
        asynchronouslyLoading();
    }

    /**
     * 异步加载
     */
    private static void asynchronouslyLoading() throws Exception{
        AsyncLoadingCache<Object, Object> async = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .buildAsync(key -> createExpensiveGraph(key));

        async.put("1", CompletableFuture.completedFuture("1"));

        CompletableFuture<Object> future = async.get("2");
        System.out.println(future);
        CompletableFuture<Object> trueResult = async.get("1");
        System.out.println(trueResult);

        System.out.println(future.get());
        System.out.println(trueResult.get());
    }



    /**
     * 手动加载
     */
    private static void manualLoading(){
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build();

        cache.put("1", "2");

        Object ifPresent = cache.getIfPresent("1");
        System.out.println(ifPresent);
        String key = null;
        Object str = cache.get(key, k -> createExpensiveGraph(k));
        System.out.println(str);

    }

    /**
     * 同步加载
     */
    private static void synchronizationLoading(){

        LoadingCache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(10)
                .build(k -> createExpensiveGraph(k));
        cache.put("1", "1");

       /* Object o1 = cache.get("1");
        System.out.println(o1);
        Object o2 = cache.get("2");
        System.out.println(o2);*/
        ArrayList<Object> keys = new ArrayList<>();
        keys.add("1");
        keys.add("2");
        keys.add("3");
        Map<Object, Object> all = cache.getAll(keys);
        System.out.println(all);
    }

    private static Object createExpensiveGraph(Object key){
        System.out.println("------------------------");
        if(null == key){
            throw new NullPointerException("");
        }
        return key + "1";
    }



}

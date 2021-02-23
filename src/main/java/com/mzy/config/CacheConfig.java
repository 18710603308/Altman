package com.mzy.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Jack Miao
 * @date 2021/1/11 17:16
 * @desc
 */
@Configuration
public class CacheConfig {

    /**
     * 必须要指定这个Bean，refreshAfterWrite=5s这个配置属性才生效
     */
    @Bean
    public CacheLoader<Object, Object> cacheLoader(){

        CacheLoader<Object, Object> cacheLoader = new CacheLoader<Object, Object>() {

            @Override
            public Object load(Object key){
                return null;
            }

            // 重写这个方法将oldValue值返回回去，进而刷新缓存
            @Override
            public Object reload(Object key, Object oldValue){
                return oldValue;
            }
        };

        return cacheLoader;
    }
}

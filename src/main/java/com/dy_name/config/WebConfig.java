package com.dy_name.config;

import com.dy_name.config.ProcessUrlAdapter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author mzy
 * @date 2021/8/2 22:02
 */

@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * 注册自定义拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProcessUrlAdapter());
        super.addInterceptors(registry);
    }
}

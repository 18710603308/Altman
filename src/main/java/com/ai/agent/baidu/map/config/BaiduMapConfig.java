package com.ai.agent.baidu.map.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 百度地图配置类
 * 用于存储百度地图API相关配置
 */
@Component
@ConfigurationProperties(prefix = "baidu.map")
public class BaiduMapConfig {

    /**
     * 百度地图API密钥
     */
    private String apiKey;

    /**
     * 百度地图API基础URL
     */
    private String baseUrl = "https://api.map.baidu.com";

    /**
     * 超时时间（毫秒）
     */
    private int timeout = 5000;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
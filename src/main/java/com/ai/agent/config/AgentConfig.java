package com.ai.agent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 智能体配置类
 */
@Configuration
@Component
public class AgentConfig {
    
    @Value("${agent.vllm.endpoint:http://localhost:8000}")
    private String vllmEndpoint;
    
    @Value("${agent.baidu.map.api.key:YOUR_API_KEY}")
    private String baiduMapApiKey;
    
    @Value("${agent.mcp.server.port:8080}")
    private int mcpServerPort;
    
    @Value("${agent.model.name:qwen3}")
    private String modelName;
    
    // Getter和Setter方法
    public String getVllmEndpoint() {
        return vllmEndpoint;
    }
    
    public void setVllmEndpoint(String vllmEndpoint) {
        this.vllmEndpoint = vllmEndpoint;
    }
    
    public String getBaiduMapApiKey() {
        return baiduMapApiKey;
    }
    
    public void setBaiduMapApiKey(String baiduMapApiKey) {
        this.baiduMapApiKey = baiduMapApiKey;
    }
    
    public int getMcpServerPort() {
        return mcpServerPort;
    }
    
    public void setMcpServerPort(int mcpServerPort) {
        this.mcpServerPort = mcpServerPort;
    }
    
    public String getModelName() {
        return modelName;
    }
    
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
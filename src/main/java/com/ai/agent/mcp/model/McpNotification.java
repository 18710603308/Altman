package com.ai.agent.mcp.model;

import lombok.Data;

@Data
public class McpNotification {
    private String method;
    private Object params;
    
    public McpNotification() {}
    
    public McpNotification(String method, Object params) {
        this.method = method;
        this.params = params;
    }
}
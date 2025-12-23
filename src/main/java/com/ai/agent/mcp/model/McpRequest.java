package com.ai.agent.mcp.model;

import lombok.Data;

@Data
public class McpRequest {
    private String method;
    private String id;
    private Object params;
    
    public McpRequest() {}
    
    public McpRequest(String method, String id, Object params) {
        this.method = method;
        this.id = id;
        this.params = params;
    }
}
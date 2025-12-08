package com.ai.agent.mcp.server.model;

import java.util.Map;

public class MCPRequest {
    private String method;
    private Map<String, Object> params;
    private String id;

    // Constructors
    public MCPRequest() {}

    public MCPRequest(String method, Map<String, Object> params, String id) {
        this.method = method;
        this.params = params;
        this.id = id;
    }

    // Getters and Setters
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
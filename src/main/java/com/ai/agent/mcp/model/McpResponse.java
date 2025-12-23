package com.ai.agent.mcp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class McpResponse {
    private String id;
    @JsonProperty("jsonrpc")
    private String jsonrpc = "2.0";
    private Object result;
    private Object error;
    
    public McpResponse() {}
    
    public McpResponse(String id, Object result) {
        this.id = id;
        this.result = result;
    }
    
    public McpResponse(String id, String error) {
        this.id = id;
        this.error = Map.of("message", error);
    }
}
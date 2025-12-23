package com.ai.agent.mcp;

import com.ai.agent.mcp.model.McpRequest;
import com.ai.agent.mcp.model.McpResponse;

public interface McpServer {
    void start();
    void stop();
    McpResponse handleRequest(McpRequest request);
    void registerHandler(String method, McpRequestHandler handler);
    
    @FunctionalInterface
    interface McpRequestHandler {
        Object handle(McpRequest request);
    }
}
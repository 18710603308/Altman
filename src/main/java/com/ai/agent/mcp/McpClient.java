package com.ai.agent.mcp;

import com.ai.agent.mcp.model.McpRequest;
import com.ai.agent.mcp.model.McpResponse;
import reactor.core.publisher.Flux;

public interface McpClient {
    McpResponse sendRequest(McpRequest request);
    Flux<McpResponse> sendStreamRequest(McpRequest request);
    void connect();
    void disconnect();
}
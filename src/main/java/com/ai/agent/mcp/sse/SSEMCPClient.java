package com.ai.agent.mcp.sse;

import com.ai.agent.mcp.MCPProtocol;
import com.ai.agent.mcp.MCPRequest;
import com.ai.agent.mcp.MCPResponse;
import com.ai.agent.mcp.MCPErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 基于SSE的MCP客户端实现
 */
public class SSEMCPClient implements MCPProtocol {
    private static final Logger logger = LoggerFactory.getLogger(SSEMCPClient.class);
    
    private final String endpoint;
    private final ObjectMapper objectMapper;
    private WebClient webClient;
    private volatile boolean connected = false;
    
    public SSEMCPClient(String endpoint) {
        this.endpoint = endpoint;
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public void initialize() {
        this.webClient = WebClient.builder()
                .baseUrl(endpoint)
                .build();
        this.connected = true;
        logger.info("SSE MCP Client initialized with endpoint: {}", endpoint);
    }
    
    @Override
    public void close() {
        this.connected = false;
        logger.info("SSE MCP Client closed");
    }
    
    @Override
    public MCPResponse sendRequest(MCPRequest request) {
        if (!connected) {
            throw new IllegalStateException("MCP client is not connected");
        }
        
        try {
            // 使用WebClient发送SSE请求
            Flux<String> eventStream = webClient.post()
                    .uri("/mcp/stream")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToFlux(String.class);
            
            // 等待第一个响应
            String responseStr = eventStream.timeout(Duration.ofSeconds(30))
                    .blockFirst();
            
            if (responseStr != null) {
                return objectMapper.readValue(responseStr, MCPResponse.class);
            } else {
                throw new RuntimeException("No response received from MCP server");
            }
        } catch (Exception e) {
            logger.error("Error sending MCP request via SSE", e);
            return new MCPResponse(request.getId(), null, 
                    new MCPErrors(-1, "Error sending request: " + e.getMessage()));
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected;
    }
}
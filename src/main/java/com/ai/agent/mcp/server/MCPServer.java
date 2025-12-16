package com.ai.agent.mcp.server;

import com.ai.agent.mcp.MCPRequest;
import com.ai.agent.mcp.MCPResponse;
import com.ai.agent.mcp.MCPErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MCP服务器端实现
 */
@RestController
@RequestMapping("/mcp")
public class MCPServer {
    private static final Logger logger = LoggerFactory.getLogger(MCPServer.class);
    
    private final ObjectMapper objectMapper;
    private final Map<String, MCPServiceHandler> serviceHandlers;
    
    public MCPServer() {
        this.objectMapper = new ObjectMapper();
        this.serviceHandlers = new ConcurrentHashMap<>();
        // 初始化内置服务处理器
        registerBuiltinServices();
    }
    
    /**
     * 注册内置服务
     */
    private void registerBuiltinServices() {
        // 这里可以注册各种MCP服务处理器
        serviceHandlers.put("ping", new PingServiceHandler());
        logger.info("Registered builtin MCP services");
    }
    
    /**
     * SSE流式请求处理端点
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> handleStreamRequest(@RequestBody MCPRequest request) {
        return Flux.fromCallable(() -> {
            try {
                MCPResponse response = processRequest(request);
                return "data: " + objectMapper.writeValueAsString(response) + "\n\n";
            } catch (Exception e) {
                logger.error("Error processing MCP request", e);
                try {
                    MCPResponse errorResponse = new MCPResponse(
                        request.getId(),
                        null,
                        new MCPErrors(-1, "Server error: " + e.getMessage())
                    );
                    return "data: " + objectMapper.writeValueAsString(errorResponse) + "\n\n";
                } catch (Exception jsonEx) {
                    return "data: {\"id\":\"" + request.getId() + "\",\"error\":{\"code\":-1,\"message\":\"Server error\"}}\n\n";
                }
            }
        }).delayElements(Duration.ofMillis(10)); // 添加微小延迟以确保SSE格式正确
    }
    
    /**
     * 标准HTTP请求处理端点
     */
    @PostMapping("/call")
    public MCPResponse handleRequest(@RequestBody MCPRequest request) {
        return processRequest(request);
    }
    
    /**
     * 处理MCP请求的核心方法
     */
    private MCPResponse processRequest(MCPRequest request) {
        logger.info("Processing MCP request: method={}, id={}", request.getMethod(), request.getId());
        
        try {
            // 查找对应的服务处理器
            MCPServiceHandler handler = serviceHandlers.get(request.getMethod());
            if (handler == null) {
                // 如果没有找到特定处理器，则返回错误
                return new MCPResponse(request.getId(), null, 
                    new MCPErrors(-32601, "Method not found: " + request.getMethod()));
            }
            
            // 执行服务调用
            Object result = handler.handle(request.getParams());
            
            // 返回成功响应
            return new MCPResponse(request.getId(), result, null);
        } catch (Exception e) {
            logger.error("Error handling MCP request", e);
            return new MCPResponse(request.getId(), null, 
                new MCPErrors(-32603, "Internal error: " + e.getMessage()));
        }
    }
    
    /**
     * 注册新的MCP服务处理器
     */
    public void registerService(String methodName, MCPServiceHandler handler) {
        serviceHandlers.put(methodName, handler);
        logger.info("Registered MCP service: {}", methodName);
    }
    
    /**
     * MCP服务处理器接口
     */
    public interface MCPServiceHandler {
        Object handle(Object params) throws Exception;
    }
    
    /**
     * 内置Ping服务处理器
     */
    private static class PingServiceHandler implements MCPServiceHandler {
        @Override
        public Object handle(Object params) throws Exception {
            return "pong";
        }
    }
}
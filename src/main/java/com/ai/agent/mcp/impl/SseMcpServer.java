package com.ai.agent.mcp.impl;

import com.ai.agent.mcp.McpServer;
import com.ai.agent.mcp.model.McpRequest;
import com.ai.agent.mcp.model.McpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RestController
@RequestMapping("/mcp")
public class SseMcpServer implements McpServer {
    private Map<String, McpRequestHandler> handlers = new HashMap<>();
    private volatile boolean running = false;

    @Override
    public void start() {
        running = true;
        System.out.println("SSE MCP Server started");
    }

    @Override
    public void stop() {
        running = false;
        System.out.println("SSE MCP Server stopped");
    }

    @Override
    public McpResponse handleRequest(McpRequest request) {
        McpRequestHandler handler = handlers.get(request.getMethod());
        if (handler != null) {
            Object result = handler.handle(request);
            return new McpResponse(request.getId(), result);
        } else {
            return new McpResponse(request.getId(), "Method not found: " + request.getMethod());
        }
    }

    @Override
    public void registerHandler(String method, McpRequestHandler handler) {
        handlers.put(method, handler);
    }

    @PostMapping("/request")
    public Mono<McpResponse> handleRequest(@RequestBody McpRequest request) {
        return Mono.fromCallable(() -> handleRequest(request));
    }

    @PostMapping("/stream")
    public Flux<ServerSentEvent<McpResponse>> handleStreamRequest(@RequestBody McpRequest request) {
        return Flux.interval(java.time.Duration.ofMillis(100))
            .take(10) // Limit for demo purposes
            .map(i -> {
                McpRequestHandler handler = handlers.get(request.getMethod());
                if (handler != null) {
                    Object result = handler.handle(request);
                    McpResponse response = new McpResponse(request.getId(), result);
                    return ServerSentEvent.builder(response).build();
                } else {
                    McpResponse response = new McpResponse(request.getId(), "Method not found: " + request.getMethod());
                    return ServerSentEvent.builder(response).build();
                }
            });
    }
}
package com.ai.agent.controller;

import com.ai.agent.mcp.McpClient;
import com.ai.agent.mcp.model.McpRequest;
import com.ai.agent.service.Qwen3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class SmartAgentController {
    
    @Autowired
    private Qwen3Service qwen3Service;
    
    @Autowired
    private McpClient sseMcpClient;
    
    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String response = qwen3Service.generateText(message);
        
        Map<String, Object> result = new HashMap<>();
        result.put("input", message);
        result.put("output", response);
        result.put("model", "Qwen3-32B");
        
        return result;
    }
    
    @GetMapping("/stream-chat")
    public SseEmitter streamChat(@RequestParam String message) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        
        try {
            Flux<org.springframework.ai.chat.model.ChatResponse> stream = qwen3Service.streamChat(message);
            
            stream.subscribe(
                response -> {
                    try {
                        emitter.send(SseEmitter.event()
                            .name("chunk")
                            .data(response.getResult().getOutput().getContent()));
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                },
                error -> emitter.completeWithError(error),
                () -> {
                    try {
                        emitter.send(SseEmitter.event().name("complete").data("Stream completed"));
                        emitter.complete();
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                }
            );
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
        
        return emitter;
    }
    
    @PostMapping("/mcp-request")
    public Map<String, Object> mcpRequest(@RequestBody Map<String, Object> request) {
        String method = (String) request.get("method");
        Object params = request.get("params");
        
        McpRequest mcpRequest = new McpRequest(method, "req-" + System.currentTimeMillis(), params);
        com.ai.agent.mcp.model.McpResponse response = sseMcpClient.sendRequest(mcpRequest);
        
        Map<String, Object> result = new HashMap<>();
        result.put("request", mcpRequest);
        result.put("response", response);
        
        return result;
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "healthy");
        health.put("model", "Qwen3-32B");
        health.put("framework", "vLLM/MindIE");
        health.put("mcp", "SSE/STDIO");
        return health;
    }
}
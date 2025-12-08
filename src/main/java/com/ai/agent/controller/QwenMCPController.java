package com.ai.agent.controller;

import com.ai.agent.mcp.client.MCPClientService;
import com.ai.agent.mcp.server.model.MCPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/qwen")
public class QwenMCPController {

    @Autowired
    private MCPClientService mcpClientService;

    @PostMapping("/completion")
    public Mono<MCPResponse> completion(
            @RequestParam String prompt,
            @RequestParam(required = false, defaultValue = "512") Integer maxTokens,
            @RequestParam(required = false, defaultValue = "0.7") Double temperature) {
        
        return mcpClientService.generateCompletion(prompt, maxTokens, temperature);
    }

    @PostMapping("/chat")
    public Mono<MCPResponse> chat(
            @RequestParam String message,
            @RequestParam(required = false) String systemPrompt,
            @RequestParam(required = false, defaultValue = "512") Integer maxTokens,
            @RequestParam(required = false, defaultValue = "0.7") Double temperature) {
        
        return mcpClientService.generateChat(systemPrompt, message, maxTokens, temperature);
    }

    @PostMapping("/embeddings")
    public Mono<MCPResponse> embeddings(@RequestParam String text) {
        return mcpClientService.generateEmbeddings(text);
    }

    @GetMapping("/health")
    public Mono<MCPResponse> health() {
        return mcpClientService.healthCheck();
    }

    // Generic MCP invoke endpoint
    @PostMapping(value = "/invoke", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MCPResponse> invoke(@RequestBody Map<String, Object> request) {
        String method = (String) request.get("method");
        Map<String, Object> params = (Map<String, Object>) request.get("params");
        
        if (method == null) {
            // Create error response
            MCPResponse.ErrorDetails error = new MCPResponse.ErrorDetails();
            error.setCode("INVALID_REQUEST");
            error.setMessage("Method is required");
            
            MCPResponse errorResponse = new MCPResponse();
            errorResponse.setError(error);
            return Mono.just(errorResponse);
        }
        
        if (params == null) {
            params = new HashMap<>();
        }
        
        return mcpClientService.invoke(method, params);
    }
}
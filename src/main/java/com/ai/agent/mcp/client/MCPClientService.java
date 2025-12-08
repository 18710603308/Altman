package com.ai.agent.mcp.client;

import com.ai.agent.mcp.server.model.MCPRequest;
import com.ai.agent.mcp.server.model.MCPResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MCPClientService {

    private final WebClient webClient;

    public MCPClientService(@Value("${mcp.server.url:http://localhost:8080/mcp/v1}") String mcpServerUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(mcpServerUrl)
                .build();
    }

    public Mono<MCPResponse> invoke(String method, Map<String, Object> params) {
        String requestId = UUID.randomUUID().toString();
        
        MCPRequest request = new MCPRequest();
        request.setId(requestId);
        request.setMethod(method);
        request.setParams(params);
        
        return webClient.post()
                .uri("/invoke")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MCPResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    MCPResponse errorResponse = new MCPResponse();
                    MCPResponse.ErrorDetails errorDetails = new MCPResponse.ErrorDetails();
                    errorDetails.setCode(String.valueOf(ex.getStatusCode().value()));
                    errorDetails.setMessage(ex.getResponseBodyAsString());
                    errorResponse.setError(errorDetails);
                    errorResponse.setId(requestId);
                    return Mono.just(errorResponse);
                });
    }

    // Convenience methods for specific Qwen operations
    public Mono<MCPResponse> generateCompletion(String prompt, Integer maxTokens, Double temperature) {
        Map<String, Object> params = new HashMap<>();
        params.put("prompt", prompt);
        if (maxTokens != null) params.put("max_tokens", maxTokens);
        if (temperature != null) params.put("temperature", temperature);
        
        return invoke("qwen/completion", params);
    }

    public Mono<MCPResponse> generateChat(String systemPrompt, String userMessage, Integer maxTokens, Double temperature) {
        Map<String, Object> params = new HashMap<>();
        params.put("message", userMessage);
        if (systemPrompt != null) params.put("system_prompt", systemPrompt);
        if (maxTokens != null) params.put("max_tokens", maxTokens);
        if (temperature != null) params.put("temperature", temperature);
        
        return invoke("qwen/chat", params);
    }

    public Mono<MCPResponse> generateEmbeddings(String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);
        
        return invoke("qwen/embeddings", params);
    }

    public Mono<MCPResponse> healthCheck() {
        return invoke("health/check", new HashMap<>());
    }
}
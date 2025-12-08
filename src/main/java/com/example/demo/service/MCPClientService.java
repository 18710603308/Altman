package com.example.demo.service;

import com.example.demo.model.MCPRequest;
import com.example.demo.model.MCPResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MCPClientService {

    private final WebClient webClient;

    public MCPClientService(@Value("${mcp.server.url:http://localhost:8080/mcp}") String serverUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(serverUrl)
                .build();
    }

    public String invokeGenerateText(String prompt) {
        MCPRequest request = new MCPRequest("generate_text", prompt, generateRequestId());
        Mono<MCPResponse> responseMono = webClient.post()
                .uri("/invoke")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MCPResponse.class);

        MCPResponse response = responseMono.block();
        if (response != null && !response.hasError()) {
            return response.getResult();
        } else if (response != null) {
            throw new RuntimeException("MCP Error: " + response.getError());
        } else {
            throw new RuntimeException("No response received from MCP server");
        }
    }

    public String invokeChatCompletion(String messages) {
        MCPRequest request = new MCPRequest("chat_completion", messages, generateRequestId());
        Mono<MCPResponse> responseMono = webClient.post()
                .uri("/invoke")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MCPResponse.class);

        MCPResponse response = responseMono.block();
        if (response != null && !response.hasError()) {
            return response.getResult();
        } else if (response != null) {
            throw new RuntimeException("MCP Error: " + response.getError());
        } else {
            throw new RuntimeException("No response received from MCP server");
        }
    }

    public String getEmbeddings(String text) {
        MCPRequest request = new MCPRequest("embeddings", text, generateRequestId());
        Mono<MCPResponse> responseMono = webClient.post()
                .uri("/invoke")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MCPResponse.class);

        MCPResponse response = responseMono.block();
        if (response != null && !response.hasError()) {
            return response.getResult();
        } else if (response != null) {
            throw new RuntimeException("MCP Error: " + response.getError());
        } else {
            throw new RuntimeException("No response received from MCP server");
        }
    }

    public String getModelInfo(String modelName) {
        MCPRequest request = new MCPRequest("model_info", modelName, generateRequestId());
        Mono<MCPResponse> responseMono = webClient.post()
                .uri("/invoke")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MCPResponse.class);

        MCPResponse response = responseMono.block();
        if (response != null && !response.hasError()) {
            return response.getResult();
        } else if (response != null) {
            throw new RuntimeException("MCP Error: " + response.getError());
        } else {
            throw new RuntimeException("No response received from MCP server");
        }
    }

    private String generateRequestId() {
        return java.util.UUID.randomUUID().toString();
    }

    public boolean checkHealth() {
        try {
            Mono<String> healthMono = webClient.get()
                    .uri("/health")
                    .retrieve()
                    .bodyToMono(String.class);

            String healthResponse = healthMono.block();
            return healthResponse != null && healthResponse.contains("healthy");
        } catch (Exception e) {
            System.err.println("Error checking MCP server health: " + e.getMessage());
            return false;
        }
    }
}
package com.ai.agent.service;

import com.ai.agent.config.QwenConfigProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.ModelRequest;
import org.springframework.ai.model.ModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class Qwen3Service {
    
    private final QwenConfigProperties configProperties;
    private ChatModel chatModel;
    
    @Autowired
    public Qwen3Service(QwenConfigProperties configProperties) {
        this.configProperties = configProperties;
        initializeChatModel();
    }
    
    private void initializeChatModel() {
        // Initialize based on the selected inference framework
        if (configProperties.getVllm().isEnabled()) {
            // For vLLM, we use OpenAI-compatible API
            initializeVllmChatModel();
        } else if (configProperties.getMindie().isEnabled()) {
            // For MindIE, we might need a custom implementation
            initializeMindieChatModel();
        }
    }
    
    private void initializeVllmChatModel() {
        // Using OpenAI-compatible client for vLLM
        org.springframework.ai.openai.OpenAiChatModel.Builder builder = 
            new org.springframework.ai.openai.OpenAiChatModel.Builder()
                .withBaseUrl(configProperties.getVllm().getBaseUrl())
                .withApiKey(configProperties.getApiKey())
                .withModel(configProperties.getModel())
                .withTemperature((float) configProperties.getTemperature())
                .withMaxTokens(configProperties.getMaxTokens());
        
        this.chatModel = builder.build();
    }
    
    private void initializeMindieChatModel() {
        // For MindIE, we would need to implement a custom client
        // This is a placeholder - actual implementation would depend on MindIE API
        org.springframework.ai.openai.OpenAiChatModel.Builder builder = 
            new org.springframework.ai.openai.OpenAiChatModel.Builder()
                .withBaseUrl(configProperties.getMindie().getBaseUrl())
                .withApiKey(configProperties.getApiKey())
                .withModel(configProperties.getModel())
                .withTemperature((float) configProperties.getTemperature())
                .withMaxTokens(configProperties.getMaxTokens());
        
        this.chatModel = builder.build();
    }
    
    public ChatResponse chat(String message) {
        Prompt prompt = new Prompt(message);
        return chatModel.call(prompt);
    }
    
    public Flux<ChatResponse> streamChat(String message) {
        Prompt prompt = new Prompt(message);
        return chatModel.stream(prompt);
    }
    
    public String generateText(String input) {
        return chatModel.call(new Prompt(input)).getResult().getOutput().getContent();
    }
}
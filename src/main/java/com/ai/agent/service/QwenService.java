package com.ai.agent.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.output.TokenUsage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QwenService {

    private ChatLanguageModel chatModel;
    private EmbeddingModel embeddingModel;

    public QwenService(
            @Value("${qwen.api.url:http://localhost:8000/v1}") String qwenApiUrl,
            @Value("${qwen.api.key:EMPTY}") String apiKey,
            @Value("${qwen.model.name:qwen3}") String modelName) {
        
        // Initialize chat model for Qwen3
        this.chatModel = OpenAiChatModel.builder()
                .baseUrl(qwenApiUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
        
        // Initialize embedding model for Qwen3
        this.embeddingModel = OpenAiEmbeddingModel.builder()
                .baseUrl(qwenApiUrl)
                .apiKey(apiKey)
                .modelName(modelName + "-embed") // Assuming embedding model name
                .build();
    }

    public String generateCompletion(String prompt, Integer maxTokens, Double temperature) {
        try {
            UserMessage userMessage = new UserMessage(prompt);
            Response<AiMessage> response = chatModel.generate(userMessage);
            
            return response.content().text();
        } catch (Exception e) {
            throw new RuntimeException("Error generating completion: " + e.getMessage(), e);
        }
    }

    public String generateChatResponse(String systemPrompt, String userMessage, Integer maxTokens, Double temperature) {
        try {
            List<dev.langchain4j.data.message.Message> messages = new ArrayList<>();
            
            if (systemPrompt != null && !systemPrompt.isEmpty()) {
                messages.add(new SystemMessage(systemPrompt));
            }
            
            messages.add(new UserMessage(userMessage));
            
            Response<AiMessage> response = chatModel.generate(messages);
            
            return response.content().text();
        } catch (Exception e) {
            throw new RuntimeException("Error generating chat response: " + e.getMessage(), e);
        }
    }

    public double[] generateEmbeddings(String text) {
        try {
            dev.langchain4j.data.embedding.Embedding embedding = embeddingModel.embed(TextSegment.from(text)).content();
            return embedding.vector();
        } catch (Exception e) {
            throw new RuntimeException("Error generating embeddings: " + e.getMessage(), e);
        }
    }

    public boolean isModelAvailable() {
        try {
            // Simple test call to check if the model is accessible
            generateCompletion("test", 10, 0.1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
package com.ai.agent.vllm;

import com.ai.agent.core.AIAgent;
import com.ai.agent.model.ChatRequest;
import com.ai.agent.model.ChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

/**
 * 基于VLLM的Qwen3模型实现
 */
public class Qwen3VLLMAgent implements AIAgent {
    private static final Logger logger = LoggerFactory.getLogger(Qwen3VLLMAgent.class);
    
    private final String vllmEndpoint;
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient httpClient;
    
    public Qwen3VLLMAgent(String vllmEndpoint) {
        this.vllmEndpoint = vllmEndpoint.endsWith("/") ? vllmEndpoint : vllmEndpoint + "/";
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClients.createDefault();
    }
    
    @Override
    public ChatResponse chat(ChatRequest request) {
        try {
            String url = vllmEndpoint + "v1/chat/completions";
            
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            
            String jsonBody = objectMapper.writeValueAsString(request);
            HttpEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                
                if (response.getCode() == 200) {
                    return objectMapper.readValue(responseBody, ChatResponse.class);
                } else {
                    throw new RuntimeException("API调用失败，状态码：" + response.getCode() + ", 响应：" + responseBody);
                }
            }
        } catch (Exception e) {
            logger.error("调用Qwen3模型时发生错误", e);
            throw new RuntimeException("调用模型失败", e);
        }
    }
    
    @Override
    public void streamChat(ChatRequest request, StreamCallback callback) {
        try {
            // 设置流式输出
            request.setStream(true);
            
            String url = vllmEndpoint + "v1/chat/completions";
            
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            
            String jsonBody = objectMapper.writeValueAsString(request);
            HttpEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            
            CloseableHttpResponse response = httpClient.execute(post);
            
            // 异步处理流式响应
            CompletableFuture.runAsync(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        
                        if (line.startsWith("data: ")) {
                            String data = line.substring(6); // 移除 "data: " 前缀
                            
                            if ("[DONE]".equals(data)) {
                                break;
                            }
                            
                            try {
                                // 解析SSE数据
                                ChatResponse chunk = objectMapper.readValue(data, ChatResponse.class);
                                
                                if (chunk.getChoices() != null && !chunk.getChoices().isEmpty()) {
                                    ChatResponse.Choice choice = chunk.getChoices().get(0);
                                    if (choice.getMessage() != null && choice.getMessage().getContent() != null) {
                                        callback.onNext(choice.getMessage().getContent());
                                    }
                                }
                            } catch (Exception e) {
                                logger.warn("解析SSE数据时出错: {}", data, e);
                            }
                        }
                    }
                    
                    callback.onComplete();
                } catch (IOException e) {
                    callback.onError(e);
                } finally {
                    try {
                        response.close();
                    } catch (IOException e) {
                        logger.warn("关闭响应时出错", e);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("流式调用Qwen3模型时发生错误", e);
            callback.onError(e);
        }
    }
    
    @Override
    public String getModelName() {
        return "qwen3";
    }
}
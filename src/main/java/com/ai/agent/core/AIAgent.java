package com.ai.agent.core;

import com.ai.agent.model.ChatRequest;
import com.ai.agent.model.ChatResponse;

/**
 * AI智能体接口
 */
public interface AIAgent {
    
    /**
     * 发送聊天请求并获取响应
     */
    ChatResponse chat(ChatRequest request);
    
    /**
     * 流式聊天（支持SSE）
     */
    void streamChat(ChatRequest request, StreamCallback callback);
    
    /**
     * 获取模型名称
     */
    String getModelName();
}

/**
 * 流式回调接口
 */
interface StreamCallback {
    void onNext(String token);
    void onError(Exception e);
    void onComplete();
}
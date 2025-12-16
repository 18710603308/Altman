package com.ai.agent.model;

import java.util.List;

/**
 * 聊天请求对象
 */
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private int maxTokens;
    private boolean stream;
    
    // 构造函数
    public ChatRequest() {
        this.temperature = 0.7;
        this.maxTokens = 1024;
        this.stream = false;
    }
    
    public ChatRequest(String model, List<Message> messages) {
        this();
        this.model = model;
        this.messages = messages;
    }
    
    public ChatRequest(String model, List<Message> messages, double temperature, int maxTokens, boolean stream) {
        this(model, messages);
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.stream = stream;
    }
    
    // Getter和Setter方法
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public List<Message> getMessages() {
        return messages;
    }
    
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public int getMaxTokens() {
        return maxTokens;
    }
    
    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }
    
    public boolean isStream() {
        return stream;
    }
    
    public void setStream(boolean stream) {
        this.stream = stream;
    }
    
    // 内部消息类
    public static class Message {
        private String role;
        private String content;
        
        public Message() {}
        
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
        
        public String getRole() {
            return role;
        }
        
        public void setRole(String role) {
            this.role = role;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
    }
}
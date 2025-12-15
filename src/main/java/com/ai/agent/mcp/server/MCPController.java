package com.ai.agent.mcp.server;

import com.ai.agent.baidu.map.handler.BaiduMapMCPHandler;
import com.ai.agent.mcp.server.model.MCPRequest;
import com.ai.agent.mcp.server.model.MCPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/mcp/v1")
public class MCPController {

    private static final Logger logger = Logger.getLogger(MCPController.class.getName());

    @Autowired
    private QwenService qwenService;
    
    @Autowired
    private BaiduMapMCPHandler baiduMapMCPHandler;

    @PostMapping("/invoke")
    public MCPResponse invoke(@RequestBody MCPRequest request) {
        logger.info("Received MCP request: " + request.getMethod());
        
        try {
            String method = request.getMethod();
            Map<String, Object> params = request.getParams() != null ? request.getParams() : new HashMap<>();
            
            // Process different MCP methods
            switch (method) {
                case "qwen/completion":
                    return handleQwenCompletion(request, params);
                case "qwen/chat":
                    return handleQwenChat(request, params);
                case "qwen/embeddings":
                    return handleQwenEmbeddings(request, params);
                case "health/check":
                    return handleHealthCheck(request);
                // 百度地图相关方法
                case "baidu/map/geocode":
                case "baidu/map/reverse_geocode":
                case "baidu/map/route":
                case "baidu/map/search_nearby":
                case "baidu/map/search_city":
                case "baidu/map/weather":
                    return baiduMapMCPHandler.handle(request).block();
                default:
                    return new MCPResponse(request.getId(), 
                        new MCPResponse.ErrorDetails("METHOD_NOT_FOUND", "Method not supported: " + method));
            }
        } catch (Exception e) {
            logger.severe("Error processing MCP request: " + e.getMessage());
            return new MCPResponse(request.getId(), 
                new MCPResponse.ErrorDetails("INTERNAL_ERROR", e.getMessage()));
        }
    }

    private MCPResponse handleQwenCompletion(MCPRequest request, Map<String, Object> params) {
        try {
            String prompt = (String) params.getOrDefault("prompt", "");
            Integer maxTokens = (Integer) params.getOrDefault("max_tokens", 512);
            Double temperature = (Double) params.getOrDefault("temperature", 0.7);
            
            String response = qwenService.generateCompletion(prompt, maxTokens, temperature);
            
            Map<String, Object> result = new HashMap<>();
            result.put("response", response);
            result.put("model", "qwen3");
            
            return new MCPResponse(request.getId(), result);
        } catch (Exception e) {
            return new MCPResponse(request.getId(), 
                new MCPResponse.ErrorDetails("COMPLETION_ERROR", e.getMessage()));
        }
    }

    private MCPResponse handleQwenChat(MCPRequest request, Map<String, Object> params) {
        try {
            String userMessage = (String) params.getOrDefault("message", "");
            String systemPrompt = (String) params.getOrDefault("system_prompt", "");
            Integer maxTokens = (Integer) params.getOrDefault("max_tokens", 512);
            Double temperature = (Double) params.getOrDefault("temperature", 0.7);
            
            String response = qwenService.generateChatResponse(systemPrompt, userMessage, maxTokens, temperature);
            
            Map<String, Object> result = new HashMap<>();
            result.put("response", response);
            result.put("model", "qwen3");
            
            return new MCPResponse(request.getId(), result);
        } catch (Exception e) {
            return new MCPResponse(request.getId(), 
                new MCPResponse.ErrorDetails("CHAT_ERROR", e.getMessage()));
        }
    }

    private MCPResponse handleQwenEmbeddings(MCPRequest request, Map<String, Object> params) {
        try {
            String text = (String) params.getOrDefault("text", "");
            
            double[] embeddings = qwenService.generateEmbeddings(text);
            
            Map<String, Object> result = new HashMap<>();
            result.put("embeddings", embeddings);
            result.put("model", "qwen3");
            result.put("dimension", embeddings.length);
            
            return new MCPResponse(request.getId(), result);
        } catch (Exception e) {
            return new MCPResponse(request.getId(), 
                new MCPResponse.ErrorDetails("EMBEDDING_ERROR", e.getMessage()));
        }
    }

    private MCPResponse handleHealthCheck(MCPRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "healthy");
        result.put("model", "qwen3");
        result.put("connected", qwenService.isModelAvailable());
        
        return new MCPResponse(request.getId(), result);
    }
}
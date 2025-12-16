package com.ai.agent.controller;

import com.ai.agent.model.ChatRequest;
import com.ai.agent.model.ChatResponse;
import com.ai.agent.service.AIAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 测试控制器，用于验证整个AI智能体系统的功能
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private AIAgentService agentService;
    
    /**
     * 测试Qwen3模型对话功能
     */
    @PostMapping("/chat")
    public ChatResponse testChat(@RequestBody ChatRequest request) {
        return agentService.chatWithQwen3(request);
    }
    
    /**
     * 测试地理编码功能（通过MCP调用百度地图服务）
     */
    @GetMapping("/geocode/{address}")
    public Object testGeocode(@PathVariable String address) {
        // 创建参数对象
        var params = new Object() {
            public String address = address;
        };
        
        // 调用MCP服务
        return agentService.callMCPViaSSE("baidu.geocoder", params, "geocode-" + System.currentTimeMillis());
    }
    
    /**
     * 测试逆地理编码功能
     */
    @GetMapping("/reverse-geocode/{lat}/{lng}")
    public Object testReverseGeocode(@PathVariable double lat, @PathVariable double lng) {
        // 创建参数对象
        var params = new Object() {
            public double latitude = lat;
            public double longitude = lng;
        };
        
        // 调用MCP服务
        return agentService.callMCPViaSSE("baidu.reverse_geocoder", params, "reverse-geocode-" + System.currentTimeMillis());
    }
    
    /**
     * 测试路径规划功能
     */
    @GetMapping("/direction/{originLat}/{originLng}/{destLat}/{destLng}")
    public Object testDirection(
            @PathVariable double originLat, 
            @PathVariable double originLng,
            @PathVariable double destLat, 
            @PathVariable double destLng) {
        
        // 创建参数对象
        var params = new Object() {
            public double origin_latitude = originLat;
            public double origin_longitude = originLng;
            public double destination_latitude = destLat;
            public double destination_longitude = destLng;
            public String mode = "driving";
        };
        
        // 调用MCP服务
        return agentService.callMCPViaSSE("baidu.direction", params, "direction-" + System.currentTimeMillis());
    }
    
    /**
     * 快速测试端点
     */
    @GetMapping("/quick-test")
    public String quickTest() {
        // 创建一个简单的聊天请求
        ChatRequest request = new ChatRequest();
        request.setModel("qwen3");
        request.setMessages(Arrays.asList(
            new ChatRequest.Message("user", "你好，请简单介绍一下自己")
        ));
        request.setTemperature(0.7);
        request.setMaxTokens(200);
        
        try {
            ChatResponse response = agentService.chatWithQwen3(request);
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                String content = response.getChoices().get(0).getMessage().getContent();
                return "Qwen3响应: " + content;
            } else {
                return "未能从Qwen3获得有效响应";
            }
        } catch (Exception e) {
            return "测试失败: " + e.getMessage();
        }
    }
}
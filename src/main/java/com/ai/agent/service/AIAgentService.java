package com.ai.agent.service;

import com.ai.agent.config.AgentConfig;
import com.ai.agent.core.AIAgent;
import com.ai.agent.mcp.MCPProtocol;
import com.ai.agent.mcp.sse.SSEMCPClient;
import com.ai.agent.mcp.stdio.STDIOClient;
import com.ai.agent.mcp.server.MCPServer;
import com.ai.agent.model.ChatRequest;
import com.ai.agent.model.ChatResponse;
import com.ai.agent.services.baidu.BaiduMapMCPService;
import com.ai.agent.vllm.Qwen3VLLMAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * AI智能体服务主类
 * 整合Qwen3模型、MCP协议和百度地图服务
 */
@Service
public class AIAgentService {
    private static final Logger logger = LoggerFactory.getLogger(AIAgentService.class);
    
    @Autowired
    private AgentConfig config;
    
    private AIAgent qwen3Agent;
    private MCPProtocol sseMcpClient;
    private MCPProtocol stdioMcpClient;
    private MCPServer mcpServer;
    private BaiduMapMCPService baiduMapService;
    
    @PostConstruct
    public void init() {
        logger.info("Initializing AI Agent Service...");
        
        // 初始化Qwen3 VLLM智能体
        this.qwen3Agent = new Qwen3VLLMAgent(config.getVllmEndpoint());
        logger.info("Qwen3 VLLM Agent initialized with endpoint: {}", config.getVllmEndpoint());
        
        // 初始化MCP服务器
        this.mcpServer = new MCPServer();
        logger.info("MCP Server initialized");
        
        // 初始化百度地图MCP服务
        this.baiduMapService = new BaiduMapMCPService(config.getBaiduMapApiKey(), mcpServer);
        logger.info("Baidu Map MCP Service initialized");
        
        // 初始化MCP客户端（SSE和STDIO两种方式）
        this.sseMcpClient = new SSEMCPClient(config.getVllmEndpoint());
        this.stdioMcpClient = new STDIOClient("echo"); // 这里使用echo作为示例，实际使用时应替换为正确的可执行文件路径
        
        // 初始化客户端连接
        try {
            sseMcpClient.initialize();
            // stdioMcpClient.initialize(); // STDIO客户端初始化可能需要特殊处理
            logger.info("MCP clients initialized");
        } catch (Exception e) {
            logger.error("Error initializing MCP clients", e);
        }
        
        logger.info("AI Agent Service initialization completed");
    }
    
    @PreDestroy
    public void destroy() {
        logger.info("Shutting down AI Agent Service...");
        
        try {
            if (baiduMapService != null) {
                baiduMapService.close();
            }
        } catch (Exception e) {
            logger.error("Error closing Baidu Map service", e);
        }
        
        try {
            if (sseMcpClient != null) {
                sseMcpClient.close();
            }
        } catch (Exception e) {
            logger.error("Error closing SSE MCP client", e);
        }
        
        try {
            if (stdioMcpClient != null) {
                stdioMcpClient.close();
            }
        } catch (Exception e) {
            logger.error("Error closing STDIO MCP client", e);
        }
        
        logger.info("AI Agent Service shutdown completed");
    }
    
    /**
     * 通过Qwen3模型进行聊天
     */
    public ChatResponse chatWithQwen3(ChatRequest request) {
        return qwen3Agent.chat(request);
    }
    
    /**
     * 通过Qwen3模型进行流式聊天
     */
    public void streamChatWithQwen3(ChatRequest request, AIAgent.StreamCallback callback) {
        qwen3Agent.streamChat(request, callback);
    }
    
    /**
     * 通过SSE方式调用MCP服务
     */
    public Object callMCPViaSSE(String method, Object params, String id) {
        return sseMcpClient.sendRequest(new com.ai.agent.mcp.MCPRequest(method, params, id));
    }
    
    /**
     * 通过STDIO方式调用MCP服务
     */
    public Object callMCPViaSTDIO(String method, Object params, String id) {
        return stdioMcpClient.sendRequest(new com.ai.agent.mcp.MCPRequest(method, params, id));
    }
    
    /**
     * 获取Qwen3智能体实例
     */
    public AIAgent getQwen3Agent() {
        return qwen3Agent;
    }
    
    /**
     * 获取MCP服务器实例
     */
    public MCPServer getMCPServer() {
        return mcpServer;
    }
}
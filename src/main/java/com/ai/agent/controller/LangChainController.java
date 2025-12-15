package com.ai.agent.controller;

import com.ai.agent.langchain.MCPToolExecutor;
import com.ai.agent.service.MCPClientService;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolExecutor;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/langchain")
public class LangChainController {

    private final MCPClientService mcpClientService;
    private final MCPToolExecutor mcpToolExecutor;
    private final ChatLanguageModel chatLanguageModel;

    public LangChainController(MCPClientService mcpClientService, MCPToolExecutor mcpToolExecutor, ChatLanguageModel chatLanguageModel) {
        this.mcpClientService = mcpClientService;
        this.mcpToolExecutor = mcpToolExecutor;
        this.chatLanguageModel = chatLanguageModel;
    }

    // 创建一个简单的AI服务，使用MCP作为工具
    @PostMapping("/chat")
    public String chatWithMCP(@RequestParam String message) {
        // 使用LangChain4j创建AI服务，集成MCP工具
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(chatLanguageModel) // 使用配置好的模型
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .tools(mcpToolExecutor)
                .build();

        return assistant.chat(message);
    }

    // 直接调用MCP工具
    @PostMapping("/generate")
    public String generateWithMCP(@RequestParam String prompt) {
        return mcpClientService.invokeGenerateText(prompt);
    }

    @PostMapping("/chat-completion")
    public String chatCompletionWithMCP(@RequestParam String messages) {
        return mcpClientService.invokeChatCompletion(messages);
    }

    @GetMapping("/health")
    public String checkMCPHealth() {
        boolean isHealthy = mcpClientService.checkHealth();
        return "MCP Server Health: " + (isHealthy ? "OK" : "NOT OK");
    }

    // 定义助手接口
    interface Assistant {
        @SystemMessage("You are a helpful assistant that can use tools to interact with an MCP server that connects to vLLM.")
        String chat(@UserMessage String userMessage);
    }
}
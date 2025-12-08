package com.example.demo.controller;

import com.example.demo.langchain.MCPToolExecutor;
import com.example.demo.service.MCPClientService;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolExecutor;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import org.springframework.web.bind.annotation.*;

import static dev.langchain4j.data.message.UserMessage.userMessage;

@RestController
@RequestMapping("/langchain")
public class LangChainController {

    private final MCPClientService mcpClientService;
    private final MCPToolExecutor mcpToolExecutor;

    public LangChainController(MCPClientService mcpClientService, MCPToolExecutor mcpToolExecutor) {
        this.mcpClientService = mcpClientService;
        this.mcpToolExecutor = mcpToolExecutor;
    }

    // 创建一个简单的AI服务，使用MCP作为工具
    @PostMapping("/chat")
    public String chatWithMCP(@RequestParam String message) {
        // 使用LangChain4j创建AI服务，集成MCP工具
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(createMockChatModel()) // 使用模拟模型，实际中可以替换为真正的模型
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

    // 创建一个模拟的聊天模型，实际应用中可以替换为真正的AI模型
    private dev.langchain4j.model.chat.ChatLanguageModel createMockChatModel() {
        // 这里我们创建一个简单的实现，实际应用中可以使用OpenAI或其他模型
        return new dev.langchain4j.model.chat.ChatLanguageModel() {
            @Override
            public dev.langchain4j.model.output.Response<dev.langchain4j.data.message.AiMessage> generate(dev.langchain4j.data.message.ChatMessage... messages) {
                String lastMessage = messages[messages.length - 1].text();
                // 简单返回消息，实际应用中应该调用AI模型
                return dev.langchain4j.model.output.Response.from(
                    dev.langchain4j.data.message.AiMessage.from("Processed by LangChain4j: " + lastMessage)
                );
            }
        };
    }

    // 定义助手接口
    interface Assistant {
        @SystemMessage("You are a helpful assistant that can use tools to interact with an MCP server that connects to vLLM.")
        String chat(@UserMessage String userMessage);
    }
}
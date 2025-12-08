package com.example.demo.langchain;

import com.example.demo.service.MCPClientService;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolExecutor;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MCPToolExecutor implements ToolExecutor {

    private final MCPClientService mcpClientService;

    public MCPToolExecutor(MCPClientService mcpClientService) {
        this.mcpClientService = mcpClientService;
    }

    // 定义工具，用于与MCP Server交互
    @Tool(name = "mcp_generate_text", description = "Generate text using vLLM through MCP server")
    public String generateText(String prompt) {
        try {
            return mcpClientService.invokeGenerateText(prompt);
        } catch (Exception e) {
            return "Error generating text: " + e.getMessage();
        }
    }

    @Tool(name = "mcp_chat_completion", description = "Get chat completion using vLLM through MCP server")
    public String chatCompletion(String messages) {
        try {
            return mcpClientService.invokeChatCompletion(messages);
        } catch (Exception e) {
            return "Error getting chat completion: " + e.getMessage();
        }
    }

    @Tool(name = "mcp_get_embeddings", description = "Get embeddings using vLLM through MCP server")
    public String getEmbeddings(String text) {
        try {
            return mcpClientService.getEmbeddings(text);
        } catch (Exception e) {
            return "Error getting embeddings: " + e.getMessage();
        }
    }

    @Override
    public String execute(ToolExecutionRequest toolExecutionRequest, Map<String, Object> variables) {
        // 这里实现自定义工具执行逻辑
        String toolName = toolExecutionRequest.name();
        String arguments = toolExecutionRequest.arguments();

        try {
            switch (toolName) {
                case "mcp_generate_text":
                    Map<String, String> args = parseArguments(arguments);
                    return mcpClientService.invokeGenerateText(args.get("prompt"));
                case "mcp_chat_completion":
                    args = parseArguments(arguments);
                    return mcpClientService.invokeChatCompletion(args.get("messages"));
                case "mcp_get_embeddings":
                    args = parseArguments(arguments);
                    return mcpClientService.getEmbeddings(args.get("text"));
                default:
                    return "Unknown tool: " + toolName;
            }
        } catch (Exception e) {
            return "Error executing tool " + toolName + ": " + e.getMessage();
        }
    }

    private Map<String, String> parseArguments(String arguments) {
        // 简单解析JSON参数
        Map<String, String> map = new ConcurrentHashMap<>();
        // 这里可以使用Jackson或其他JSON库来解析参数
        // 简化处理：假设参数格式为 {"key": "value"}
        arguments = arguments.replaceAll("[{}\"]", "");
        String[] pairs = arguments.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }
}
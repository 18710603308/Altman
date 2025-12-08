# MCP Server with vLLM + LangChain4j Client

这是一个集成了MCP (Model Context Protocol) Server、vLLM后端和LangChain4j客户端的完整系统。

## 项目结构

- **MCP Server**: 提供标准化API接口，可连接到vLLM或其他大语言模型
- **MCP Client**: 调用MCP Server的客户端服务
- **LangChain4j Integration**: 集成LangChain4j框架，支持工具调用

## 功能特性

### MCP Server (Controller)
- `/mcp/invoke` - 处理MCP请求 (generate_text, chat_completion, embeddings, model_info)
- `/mcp/health` - 健康检查端点

### MCP Client (Service)
- 通过WebClient与MCP Server通信
- 支持多种AI操作 (文本生成、对话、嵌入等)
- 错误处理和健康检查

### LangChain4j Integration
- 通过MCP工具与AI模型交互
- 支持工具调用模式
- 集成聊天记忆功能

## API端点

### MCP Server
- `POST /mcp/invoke` - 执行MCP请求
- `GET /mcp/health` - 检查服务健康状态

### LangChain4j Client
- `POST /langchain/chat` - 与集成MCP工具的AI助手聊天
- `POST /langchain/generate` - 直接生成文本
- `POST /langchain/chat-completion` - 获取对话补全
- `GET /langchain/health` - 检查MCP服务器健康状态

## 配置

在 `application.properties` 中配置:
```properties
# 服务器端口
server.port=8080

# MCP服务器URL
mcp.server.url=http://localhost:8080/mcp
```

## 如何运行

1. 启动Spring Boot应用:
```bash
mvn spring-boot:run
```

2. 或者打包后运行:
```bash
mvn package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## 示例请求

### 使用cURL测试MCP Server:
```bash
curl -X POST http://localhost:8080/mcp/invoke \
  -H "Content-Type: application/json" \
  -d '{
    "method": "generate_text", 
    "params": "Hello, world!", 
    "id": "req-123"
  }'
```

### 使用LangChain4j客户端:
```bash
curl -X POST "http://localhost:8080/langchain/chat?message=Generate%20a%20story%20about%20AI"
```

## 设计模式

- **MCP协议**: 标准化的模型交互协议
- **微服务架构**: 服务分离，易于扩展
- **响应式编程**: 使用Spring WebFlux进行异步处理
- **工具集成**: LangChain4j工具调用模式

## 依赖项

- Spring Boot Web
- Spring Boot WebFlux
- Spring AI
- LangChain4j
- Jackson Databind
- Spring Boot Actuator

## 应用场景

- AI模型服务化
- 统一模型接口
- 工具增强AI能力
- 微服务架构下的AI集成
# Qwen3 MCP Server and Client

This project provides a Model Context Protocol (MCP) server and client implementation specifically designed to interface with local Qwen3 models.

## Overview

The system consists of:
- **MCP Server**: Provides standardized endpoints to interact with Qwen3 models
- **MCP Client**: Allows applications to communicate with the MCP server
- **Qwen Service**: Handles communication with the local Qwen3 model via OpenAI-compatible API

## Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│   Client App    │───▶│   MCP Server     │───▶│   Qwen3 Model    │
│                 │    │                  │    │ (via vLLM/Ollama)│
└─────────────────┘    └──────────────────┘    └──────────────────┘
                            │
                     ┌──────────────────┐
                     │   MCP Client     │
                     │ (for other apps) │
                     └──────────────────┘
```

## Prerequisites

- Java 17+
- Maven 3.6+
- Local Qwen3 model server (e.g., running via vLLM or Ollama) at `http://localhost:8000/v1`

## Configuration

The application can be configured via `application.properties`:

```properties
# Qwen API configuration - adjust these to match your local Qwen3 setup
qwen.api.url=http://localhost:8000/v1
qwen.api.key=EMPTY
qwen.model.name=qwen3

# MCP Server URL for the client to connect to
mcp.server.url=http://localhost:8080/mcp/v1
```

## Running the Application

1. **Start your local Qwen3 server** (e.g., using vLLM):
   ```bash
   python -m vllm.entrypoints.openai.api_server \
       --model Qwen/Qwen3-7B-Instruct \
       --served-model-name qwen3
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### MCP Server Endpoints

The MCP server is available at `http://localhost:8080/mcp/v1/invoke`

**Supported Methods:**

1. **Qwen Completion**:
   ```json
   {
     "method": "qwen/completion",
     "params": {
       "prompt": "Write a poem about AI",
       "max_tokens": 256,
       "temperature": 0.7
     },
     "id": "req-123"
   }
   ```

2. **Qwen Chat**:
   ```json
   {
     "method": "qwen/chat",
     "params": {
       "message": "What is machine learning?",
       "system_prompt": "You are a helpful AI assistant",
       "max_tokens": 256,
       "temperature": 0.7
     },
     "id": "req-124"
   }
   ```

3. **Qwen Embeddings**:
   ```json
   {
     "method": "qwen/embeddings",
     "params": {
       "text": "This is a sample text for embedding"
     },
     "id": "req-125"
   }
   ```

4. **Health Check**:
   ```json
   {
     "method": "health/check",
     "params": {},
     "id": "req-126"
   }
   ```

### Application API Endpoints

The application also provides convenience endpoints at `http://localhost:8080/api/qwen/`:

- `POST /api/qwen/completion?prompt={prompt}` - Generate text completion
- `POST /api/qwen/chat?message={message}` - Chat with the model
- `POST /api/qwen/embeddings?text={text}` - Generate embeddings
- `GET /api/qwen/health` - Check health status
- `POST /api/qwen/invoke` - Generic MCP invoke

## Example Usage

### Using cURL

1. **Completion**:
   ```bash
   curl -X POST http://localhost:8080/api/qwen/completion \
     -d "prompt=Explain quantum computing in simple terms" \
     -d "maxTokens=100" \
     -d "temperature=0.7"
   ```

2. **Chat**:
   ```bash
   curl -X POST http://localhost:8080/api/qwen/chat \
     -d "message=What are the benefits of renewable energy?" \
     -d "systemPrompt=You are an environmental expert"
   ```

3. **Direct MCP call**:
   ```bash
   curl -X POST http://localhost:8080/mcp/v1/invoke \
     -H "Content-Type: application/json" \
     -d '{
       "method": "qwen/chat",
       "params": {
         "message": "How does photosynthesis work?",
         "max_tokens": 150
       },
       "id": "test-123"
     }'
   ```

### Using the MCP Client in Code

```java
@Autowired
private MCPClientService mcpClient;

// Generate a completion
Mono<MCPResponse> response = mcpClient.generateCompletion(
    "Write a short story about a robot", 200, 0.8);

// Or use the generic invoke method
Map<String, Object> params = new HashMap<>();
params.put("prompt", "Translate 'Hello' to French");
Mono<MCPResponse> response = mcpClient.invoke("qwen/completion", params);
```

## Integration with Local Qwen3 Models

The application is designed to work with local Qwen3 models served through APIs compatible with OpenAI's format. This includes:

- **vLLM**: Most common deployment option
- **Ollama**: Alternative lightweight option
- **TGI (Text Generation Inference)**: Hugging Face solution

Make sure your local Qwen3 server is running and accessible before starting the application.

## Error Handling

The MCP server returns structured error responses in the following format:

```json
{
  "id": "req-123",
  "error": {
    "code": "ERROR_CODE",
    "message": "Error description"
  }
}
```

## Development

To extend the functionality:

1. Add new methods to `MCPController` to support additional operations
2. Implement corresponding methods in `QwenService` to interact with the model
3. Add convenience methods to `MCPClientService` for easier client usage

## Troubleshooting

1. **Connection Issues**: Ensure your local Qwen3 server is running at the configured URL
2. **Model Not Found**: Verify the model name matches what's served by your local server
3. **Authentication Issues**: For local servers, API key is often set to "EMPTY" or can be omitted

## License

This project is licensed under the MIT License.
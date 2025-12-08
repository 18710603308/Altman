package com.ai.agent.mcp.client;

import com.ai.agent.mcp.server.model.MCPResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource(properties = {
    "qwen.api.url=http://localhost:8080/v1", // This should fail in tests, which is expected
    "mcp.server.url=http://localhost:9999/mcp/v1" // Use non-existent server to test error handling
})
class MCPClientServiceTest {

    @Autowired
    private MCPClientService mcpClientService;

    @Test
    void testInvokeWithNonExistentServer() {
        // This test verifies that the client handles connection errors properly
        Map<String, Object> params = new HashMap<>();
        params.put("prompt", "test");
        
        Mono<MCPResponse> responseMono = mcpClientService.invoke("qwen/completion", params);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getError() != null)
                .verifyComplete();
    }
}
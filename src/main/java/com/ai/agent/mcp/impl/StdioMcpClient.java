package com.ai.agent.mcp.impl;

import com.ai.agent.mcp.McpClient;
import com.ai.agent.mcp.model.McpRequest;
import com.ai.agent.mcp.model.McpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.*;
import java.util.concurrent.CompletableFuture;

@Component
public class StdioMcpClient implements McpClient {
    private Process process;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ObjectMapper objectMapper = new ObjectMapper();
    private boolean connected = false;

    @Override
    public McpResponse sendRequest(McpRequest request) {
        if (!connected) {
            throw new IllegalStateException("MCP client not connected");
        }
        
        try {
            // Serialize request to JSON
            String jsonRequest = objectMapper.writeValueAsString(request);
            
            // Write to STDIO
            writer.write(jsonRequest + "\n");
            writer.flush();
            
            // Read response
            String jsonResponse = reader.readLine();
            if (jsonResponse != null) {
                return objectMapper.readValue(jsonResponse, McpResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public Flux<McpResponse> sendStreamRequest(McpRequest request) {
        // For STDIO, streaming is implemented through continuous reading
        // This is a simplified implementation
        return Flux.create(sink -> {
            try {
                String jsonRequest = objectMapper.writeValueAsString(request);
                writer.write(jsonRequest + "\n");
                writer.flush();
                
                // Continuously read responses
                String line;
                while ((line = reader.readLine()) != null && !sink.isCancelled()) {
                    McpResponse response = objectMapper.readValue(line, McpResponse.class);
                    sink.next(response);
                }
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }

    @Override
    public void connect() {
        try {
            // Start an external process that implements MCP via STDIO
            ProcessBuilder processBuilder = new ProcessBuilder("echo", "MCP process started");
            process = processBuilder.start();
            
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            
            connected = true;
            System.out.println("STDIO MCP Client connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (process != null) {
                process.destroy();
            }
            connected = false;
            System.out.println("STDIO MCP Client disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
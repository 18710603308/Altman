package com.ai.agent.mcp.stdio;

import com.ai.agent.mcp.MCPProtocol;
import com.ai.agent.mcp.MCPRequest;
import com.ai.agent.mcp.MCPResponse;
import com.ai.agent.mcp.MCPErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

/**
 * 基于STDIO的MCP客户端实现
 */
public class STDIOClient implements MCPProtocol {
    private static final Logger logger = LoggerFactory.getLogger(STDIOClient.class);
    
    private Process process;
    private BufferedWriter writer;
    private Scanner scanner;
    private final ObjectMapper objectMapper;
    private final String executablePath;
    private volatile boolean connected = false;
    
    public STDIOClient(String executablePath) {
        this.executablePath = executablePath;
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public void initialize() {
        try {
            ProcessBuilder pb = new ProcessBuilder(executablePath);
            pb.redirectErrorStream(true);
            this.process = pb.start();
            
            this.writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            this.scanner = new Scanner(process.getInputStream());
            
            // 启动读取线程
            Thread readerThread = new Thread(this::readResponses);
            readerThread.setDaemon(true);
            readerThread.start();
            
            this.connected = true;
            logger.info("STDIO MCP Client initialized with executable: {}", executablePath);
        } catch (IOException e) {
            logger.error("Error initializing STDIO MCP Client", e);
            throw new RuntimeException("Failed to initialize STDIO MCP Client", e);
        }
    }
    
    @Override
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (scanner != null) {
                scanner.close();
            }
            if (process != null) {
                process.destroy();
            }
            this.connected = false;
            logger.info("STDIO MCP Client closed");
        } catch (Exception e) {
            logger.error("Error closing STDIO MCP Client", e);
        }
    }
    
    @Override
    public MCPResponse sendRequest(MCPRequest request) {
        if (!connected) {
            throw new IllegalStateException("MCP client is not connected");
        }
        
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            writer.write(requestJson);
            writer.newLine();
            writer.flush();
            
            // 等待响应（这里简化实现，实际中可能需要更复杂的逻辑来匹配ID）
            if (scanner.hasNextLine()) {
                String responseJson = scanner.nextLine();
                return objectMapper.readValue(responseJson, MCPResponse.class);
            } else {
                throw new RuntimeException("No response received from MCP server");
            }
        } catch (Exception e) {
            logger.error("Error sending MCP request via STDIO", e);
            return new MCPResponse(request.getId(), null, 
                    new MCPErrors(-1, "Error sending request: " + e.getMessage()));
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected && process.isAlive();
    }
    
    /**
     * 读取来自子进程的响应
     */
    private void readResponses() {
        while (connected && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // 处理接收到的响应
            logger.debug("Received response from MCP: {}", line);
        }
    }
}
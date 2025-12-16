package com.ai.agent.mcp;

/**
 * MCP（Model Context Protocol）协议基础接口
 */
public interface MCPProtocol {
    
    /**
     * 初始化MCP连接
     */
    void initialize();
    
    /**
     * 关闭MCP连接
     */
    void close();
    
    /**
     * 发送请求并接收响应
     */
    MCPResponse sendRequest(MCPRequest request);
    
    /**
     * 检查连接是否活跃
     */
    boolean isConnected();
}

/**
 * MCP请求对象
 */
class MCPRequest {
    private String method;
    private Object params;
    private String id;
    
    public MCPRequest(String method, Object params, String id) {
        this.method = method;
        this.params = params;
        this.id = id;
    }
    
    // Getter和Setter方法
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public Object getParams() {
        return params;
    }
    
    public void setParams(Object params) {
        this.params = params;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}

/**
 * MCP响应对象
 */
class MCPResponse {
    private String id;
    private Object result;
    private MCPErrors error;
    
    public MCPResponse(String id, Object result, MCPErrors error) {
        this.id = id;
        this.result = result;
        this.error = error;
    }
    
    // Getter和Setter方法
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Object getResult() {
        return result;
    }
    
    public void setResult(Object result) {
        this.result = result;
    }
    
    public MCPErrors getError() {
        return error;
    }
    
    public void setError(MCPErrors error) {
        this.error = error;
    }
}

/**
 * MCP错误定义
 */
class MCPErrors {
    private int code;
    private String message;
    
    public MCPErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    // Getter和Setter方法
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
package com.ai.agent.model;

public class MCPResponse {
    private String result;
    private String id;
    private String error;

    // 构造函数
    public MCPResponse() {}

    public MCPResponse(String result, String id) {
        this.result = result;
        this.id = id;
    }

    public MCPResponse(String error, String id, boolean isError) {
        if (isError) {
            this.error = error;
            this.id = id;
        }
    }

    // Getter和Setter方法
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}
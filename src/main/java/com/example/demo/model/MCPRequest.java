package com.example.demo.model;

public class MCPRequest {
    private String method;
    private String params;
    private String id;

    // 构造函数
    public MCPRequest() {}

    public MCPRequest(String method, String params, String id) {
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
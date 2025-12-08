package com.ai.agent.mcp.server.model;

import java.util.Map;

public class MCPResponse {
    private String id;
    private Map<String, Object> result;
    private ErrorDetails error;

    // Constructors
    public MCPResponse() {}

    public MCPResponse(String id, Map<String, Object> result) {
        this.id = id;
        this.result = result;
    }

    public MCPResponse(String id, ErrorDetails error) {
        this.id = id;
        this.error = error;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public ErrorDetails getError() {
        return error;
    }

    public void setError(ErrorDetails error) {
        this.error = error;
    }

    public static class ErrorDetails {
        private String code;
        private String message;

        public ErrorDetails() {}

        public ErrorDetails(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
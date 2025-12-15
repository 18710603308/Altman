package com.ai.agent.exception;

public class MCPException extends RuntimeException {
    public MCPException(String message) {
        super(message);
    }

    public MCPException(String message, Throwable cause) {
        super(message, cause);
    }
}
package com.altman.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.dashscope.api.DashScopeChatModel;
import org.springframework.ai.deepseek.api.DeepSeekChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {
    private final DashScopeChatModel dashScopeChatModel;
    private final DeepSeekChatModel deepSeekChatModel;

    @GetMapping("/dashscope/chat")
    public Object dashscopeChat(@RequestParam String message) {
        return dashScopeChatModel.call(message);
    }

    @GetMapping("/deepseek/chat")
    public Object deepseekChat(@RequestParam String message) {
        return deepSeekChatModel.call(message);
    }
} 
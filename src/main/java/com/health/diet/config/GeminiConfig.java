package com.health.diet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {
    @Value("${gemini.api.key}")
    public String geminiApiKey;

    @Value("${gemini.model}")
    public String geminiModel;

    @Value("${gemini.api.url}")
    public String geminiApiUrl;
}

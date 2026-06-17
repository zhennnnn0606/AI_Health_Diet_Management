package com.health.diet.service;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeAIException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.health.diet.config.GeminiConfig;
import com.health.diet.dto.MealAnalysisResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GeminiService {
    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);
    private final GeminiConfig geminiConfig;
    private final Gson gson;

    public GeminiService(GeminiConfig geminiConfig) {
        this.geminiConfig = geminiConfig;
        this.gson = new Gson();
    }

    public MealAnalysisResponse analyzeMeal(String mealDescription) {
        try {
            GenerativeModel model = new GenerativeModel(
                geminiConfig.geminiModel,
                geminiConfig.geminiApiKey
            );

            String systemPrompt = "你是一個專業的營養師。分析用戶提供的餐食描述，並以以下JSON格式回應（僅返回JSON，不要有任何其他文字）:\\n" +
                "{\\\"mealName\\\":\\\"餐食名稱\\\",\\\"nutritionAnalysis\\\":\\\"營養分析\\\",\\\"healthScore\\\":健康評分(1-10),\\\"recommendations\\\":\\\"改進建議\\\",\\\"ingredients\\\":[\\\"成分1\\\",\\\"成分2\\\"]}";

            String userPrompt = "請分析這個餐食：" + mealDescription;

            String responseText = model.generateContent(systemPrompt + "\\n" + userPrompt)
                .getContent()
                .getParts()
                .get(0)
                .getAsText();

            String cleanedResponse = responseText.trim();
            if (cleanedResponse.startsWith("```json")) {
                cleanedResponse = cleanedResponse.substring(7);
            }
            if (cleanedResponse.startsWith("```")) {
                cleanedResponse = cleanedResponse.substring(3);
            }
            if (cleanedResponse.endsWith("```")) {
                cleanedResponse = cleanedResponse.substring(0, cleanedResponse.length() - 3);
            }
            cleanedResponse = cleanedResponse.trim();

            return gson.fromJson(cleanedResponse, MealAnalysisResponse.class);
        } catch (JsonSyntaxException e) {
            logger.error("JSON解析失敗", e);
            throw new RuntimeException("AI響應格式不正確");
        } catch (GenerativeAIException e) {
            logger.error("Gemini API 調用失敗", e);
            throw new RuntimeException("AI API 調用失敗：" + e.getMessage());
        } catch (Exception e) {
            logger.error("未預期的錯誤", e);
            throw new RuntimeException("分析失敗：" + e.getMessage());
        }
    }
}

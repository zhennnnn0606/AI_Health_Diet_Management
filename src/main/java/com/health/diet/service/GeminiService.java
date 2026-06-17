package com.health.diet.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
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
    private final Client client; // 新版 SDK 使用 Client 進行連線

    public GeminiService(GeminiConfig geminiConfig) {
        this.geminiConfig = geminiConfig;
        this.gson = new Gson();
        // 在建構子初始化 Google GenAI Client
        this.client = Client.builder().apiKey(geminiConfig.geminiApiKey).build();
    }

    public MealAnalysisResponse analyzeMeal(String mealDescription) {
        try {
            String systemPrompt = "你是一個專業的營養師。分析用戶提供的餐食描述，並以以下JSON格式回應（僅返回JSON，不要有任何其他文字）:\n" +
                "{\"mealName\":\"餐食名稱\",\"nutritionAnalysis\":\"營養分析\",\"healthScore\":健康評分(1-10),\"recommendations\":\"改進建議\",\"ingredients\":[\"成分1\",\"成分2\"]}";

            String userPrompt = "請分析這個餐食：" + mealDescription;

            // 使用新版 SDK 的語法來生成內容
            // 使用新版 SDK 的語法來生成內容
            GenerateContentResponse response = client.models.generateContent(
                geminiConfig.geminiModel,
                systemPrompt + "\n" + userPrompt,
                null // <--- 加上這個！代表不使用自訂設定，直接用預設值
            );

            // 取得回傳的文字
            String responseText = response.text();

            // 處理與清理 JSON 字串
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
        } catch (Exception e) { 
            // 新版 SDK 拋出的例外已不同，使用通用 Exception 捕捉即可
            logger.error("未預期的錯誤", e);
            throw new RuntimeException("分析失敗：" + e.getMessage());
        }
    }
}
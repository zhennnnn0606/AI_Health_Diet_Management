package com.health.diet.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MealAnalysisResponse {
    @SerializedName("mealName")
    private String mealName;

    @SerializedName("nutritionAnalysis")
    private String nutritionAnalysis;

    @SerializedName("healthScore")
    private int healthScore;

    @SerializedName("recommendations")
    private String recommendations;

    @SerializedName("ingredients")
    private List<String> ingredients;

    public MealAnalysisResponse() {}

    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public String getNutritionAnalysis() { return nutritionAnalysis; }
    public void setNutritionAnalysis(String nutritionAnalysis) { this.nutritionAnalysis = nutritionAnalysis; }

    public int getHealthScore() { return healthScore; }
    public void setHealthScore(int healthScore) { this.healthScore = healthScore; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
}

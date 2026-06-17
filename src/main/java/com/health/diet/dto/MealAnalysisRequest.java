package com.health.diet.dto;

public class MealAnalysisRequest {
    private String mealDescription;

    public MealAnalysisRequest() {}

    public MealAnalysisRequest(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }
}

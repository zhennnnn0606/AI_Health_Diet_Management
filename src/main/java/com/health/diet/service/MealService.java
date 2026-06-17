package com.health.diet.service;

import com.health.diet.dto.MealAnalysisResponse;
import com.health.diet.entity.Meal;
import com.health.diet.repository.MealRepository;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import java.util.List;

@Service
public class MealService {
    private final GeminiService geminiService;
    private final MealRepository mealRepository;
    private final Gson gson;

    public MealService(GeminiService geminiService, MealRepository mealRepository) {
        this.geminiService = geminiService;
        this.mealRepository = mealRepository;
        this.gson = new Gson();
    }

    public MealAnalysisResponse analyzeMealAndSave(String mealDescription) {
        MealAnalysisResponse analysis = geminiService.analyzeMeal(mealDescription);
        
        Meal meal = new Meal();
        meal.setMealName(analysis.getMealName());
        meal.setNutritionAnalysis(analysis.getNutritionAnalysis());
        meal.setHealthScore(analysis.getHealthScore());
        meal.setRecommendations(analysis.getRecommendations());
        meal.setIngredients(gson.toJson(analysis.getIngredients()));
        meal.setOriginalDescription(mealDescription);
        
        mealRepository.save(meal);
        return analysis;
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }
}

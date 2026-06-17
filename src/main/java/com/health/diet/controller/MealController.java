package com.health.diet.controller;

import com.health.diet.dto.MealAnalysisRequest;
import com.health.diet.dto.MealAnalysisResponse;
import com.health.diet.entity.Meal;
import com.health.diet.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MealController {
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<MealAnalysisResponse> analyzeMeal(@RequestBody MealAnalysisRequest request) {
        try {
            MealAnalysisResponse response = mealService.analyzeMealAndSave(request.getMealDescription());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<Meal>> getMealHistory() {
        List<Meal> meals = mealService.getAllMeals();
        return ResponseEntity.ok(meals);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        try {
            mealService.deleteMeal(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

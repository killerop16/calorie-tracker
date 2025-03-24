package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.MealCreateRequestDto;
import com.example.calorieTracker.dto.MealResponseDto;
import java.util.List;

public interface MealService {
    MealResponseDto addMeal(MealCreateRequestDto request);
    MealResponseDto getMeal(Long id);
    List<MealResponseDto> getMealsByUser(Long userId);
    void deleteMeal(Long id);
}

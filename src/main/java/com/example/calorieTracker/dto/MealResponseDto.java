package com.example.calorieTracker.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MealResponseDto {
    private Long mealId;
    private Long userId;
    private LocalDateTime mealTime;
    private List<FoodResponseDto> foodItems;
    private int totalCalories;
}

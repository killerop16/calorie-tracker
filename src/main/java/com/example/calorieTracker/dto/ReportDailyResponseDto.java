package com.example.calorieTracker.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ReportDailyResponseDto {
    private Long userId;
    private String date;
    private int totalCalories;
    private List<MealResponseDto> meals;
    private boolean withinCalorieLimit;
}

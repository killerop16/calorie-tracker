package com.example.calorieTracker.dto;

import lombok.Data;

@Data
public class FoodResponseDto {
    private String name;
    private int calories;
    private double proteins;
    private double fats;
    private double carbs;
}

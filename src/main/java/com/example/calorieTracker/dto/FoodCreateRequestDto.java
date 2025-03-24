package com.example.calorieTracker.dto;

import com.example.calorieTracker.annotation.NotBlankName;
import com.example.calorieTracker.annotation.ValidName;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FoodCreateRequestDto {
    @NotBlankName
    @ValidName
    private String name;

    @Positive
    private int calories;

    @PositiveOrZero
    private double proteins;

    @PositiveOrZero
    private double fats;

    @PositiveOrZero
    private double carbs;
}

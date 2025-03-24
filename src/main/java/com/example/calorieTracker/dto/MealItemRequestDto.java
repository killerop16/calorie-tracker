package com.example.calorieTracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealItemRequestDto {
    @NotNull(message = "Food ID must not be null")
    @Positive(message = "Food ID must be a positive number")
    private Long foodId;

    @Positive
    private int quantity;
}

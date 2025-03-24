package com.example.calorieTracker.dto;

import com.example.calorieTracker.annotation.PositiveUserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MealCreateRequestDto {
    @NotNull(message = "User ID must not be null")
    @PositiveUserId
    private Long userId;

    @NotNull(message = "Meal time must not be null")
    @FutureOrPresent(message = "Meal time must be in the current or future time")
    private LocalDateTime mealTime;

    @NotEmpty(message = "Food items list must not be empty")
    @Valid
    private List<MealItemRequestDto> foodItems;
}

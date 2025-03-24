package com.example.calorieTracker.controller;

import com.example.calorieTracker.annotation.PositiveId;
import com.example.calorieTracker.annotation.PositiveUserId;
import com.example.calorieTracker.dto.MealCreateRequestDto;
import com.example.calorieTracker.dto.MealResponseDto;
import com.example.calorieTracker.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
@Validated
public class MealController {
    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealResponseDto> addMeal(@Valid @RequestBody MealCreateRequestDto mealRequest) {
        return ResponseEntity.ok(mealService.addMeal(mealRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDto> getMeal(@PathVariable @PositiveId Long id) {
        return ResponseEntity.ok(mealService.getMeal(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealResponseDto>> getMealsByUser(
            @PathVariable @PositiveUserId Long userId) {
        return ResponseEntity.ok(mealService.getMealsByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable @PositiveId Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}

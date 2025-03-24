package com.example.calorieTracker.controller;

import com.example.calorieTracker.annotation.PositiveId;
import com.example.calorieTracker.dto.FoodCreateRequestDto;
import com.example.calorieTracker.dto.FoodResponseDto;
import com.example.calorieTracker.dto.FoodUpdateRequestDto;
import com.example.calorieTracker.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
@Validated
public class FoodController {
    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<FoodResponseDto> createFood(@Valid @RequestBody FoodCreateRequestDto request) {
        return ResponseEntity.ok(foodService.createFood(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDto> getFood(@PathVariable @PositiveId Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @GetMapping
    public ResponseEntity<List<FoodResponseDto>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodResponseDto> updateFood(
            @PathVariable @PositiveId Long id,
            @Valid @RequestBody FoodUpdateRequestDto request) {
        return ResponseEntity.ok(foodService.updateFood(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable @PositiveId Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }
}

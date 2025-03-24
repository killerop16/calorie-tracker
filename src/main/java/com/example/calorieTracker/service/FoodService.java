package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.FoodCreateRequestDto;
import com.example.calorieTracker.dto.FoodResponseDto;
import com.example.calorieTracker.dto.FoodUpdateRequestDto;
import java.util.List;

public interface FoodService {
    FoodResponseDto createFood(FoodCreateRequestDto request);
    FoodResponseDto getFoodById(Long id);
    List<FoodResponseDto> getAllFoods();
    FoodResponseDto updateFood(Long id, FoodUpdateRequestDto request);
    void deleteFood(Long id);
}

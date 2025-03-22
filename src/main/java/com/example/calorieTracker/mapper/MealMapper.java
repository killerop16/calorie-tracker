package com.example.calorieTracker.mapper;

import com.example.calorieTracker.dto.MealCreateRequestDto;
import com.example.calorieTracker.dto.MealResponseDto;
import com.example.calorieTracker.model.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FoodMapper.class})
public interface MealMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "foodItems", ignore = true)
    Meal mapCreateRequestToMeal(MealCreateRequestDto request);

    @Mapping(target = "mealId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "foodItems", source = "foodItems")
    @Mapping(target = "totalCalories", expression = "java(meal.getFoodItems().stream().mapToInt(FoodItem::getCalories).sum())")
    MealResponseDto mapMealToResponse(Meal meal);

}


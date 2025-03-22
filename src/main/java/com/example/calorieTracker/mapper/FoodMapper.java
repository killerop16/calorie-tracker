package com.example.calorieTracker.mapper;

import com.example.calorieTracker.dto.FoodCreateRequestDto;
import com.example.calorieTracker.dto.FoodResponseDto;
import com.example.calorieTracker.dto.FoodUpdateRequestDto;
import com.example.calorieTracker.model.FoodItem;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    @Mapping(target = "id", ignore = true)
    FoodItem mapCreateRequestToFoodItem(FoodCreateRequestDto request);

    FoodResponseDto mapFoodItemToResponse(FoodItem foodItem);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFoodItemFromRequest(FoodUpdateRequestDto request, @MappingTarget FoodItem foodItem);

}

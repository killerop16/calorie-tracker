package com.example.calorieTracker.service.impl;

import com.example.calorieTracker.dto.FoodCreateRequestDto;
import com.example.calorieTracker.dto.FoodResponseDto;
import com.example.calorieTracker.dto.FoodUpdateRequestDto;
import com.example.calorieTracker.exception.NotFoundException;
import com.example.calorieTracker.mapper.FoodMapper;
import com.example.calorieTracker.repository.FoodRepository;
import com.example.calorieTracker.service.FoodService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.calorieTracker.constants.Messages.FOOD_ITEM_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    @Override
    public FoodResponseDto createFood(FoodCreateRequestDto request) {
        log.info("Creating new food item: {}", request.getName());
        if (foodRepository.findByName(request.getName()).isPresent()) {
            log.warn("Food item with name '{}' already exists", request.getName());
            throw new IllegalArgumentException("A food item with this name already exists");
        }
        var foodItem = foodMapper.mapCreateRequestToFoodItem(request);
        foodItem = foodRepository.save(foodItem);
        log.info("Food item '{}' successfully created with ID={}", foodItem.getName(), foodItem.getId());
        return foodMapper.mapFoodItemToResponse(foodItem);
    }

    @Override
    public FoodResponseDto getFoodById(Long id) {
        log.info("Fetching food item with ID={}", id);
        var foodItem = foodRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Food item with ID={} not found", id);
                    return new EntityNotFoundException(FOOD_ITEM_NOT_FOUND);
                });
        log.info("Food item with ID={} found: {}", id, foodItem.getName());
        return foodMapper.mapFoodItemToResponse(foodItem);
    }

    @Override
    public List<FoodResponseDto> getAllFoods() {
        log.info("Fetching all food items");
        var foodList = foodRepository.findAll()
                .stream()
                .map(foodMapper::mapFoodItemToResponse)
                .collect(Collectors.toList());
        log.info("Found {} food items", foodList.size());
        return foodList;
    }

    @Override
    public FoodResponseDto updateFood(Long id, FoodUpdateRequestDto request) {
        log.info("Updating food item with ID={}", id);
        var foodItem = foodRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Food item with ID={} not found", id);
                    return new NotFoundException(FOOD_ITEM_NOT_FOUND);
                });

        foodMapper.updateFoodItemFromRequest(request, foodItem);
        foodItem = foodRepository.save(foodItem);
        log.info("Food item with ID={} successfully updated", id);

        return foodMapper.mapFoodItemToResponse(foodItem);
    }

    @Override
    public void deleteFood(Long id) {
        log.info("Deleting food item with ID={}", id);
        if (!foodRepository.existsById(id)) {
            log.error("Attempt to delete non-existing food item with ID={}", id);
            throw new NotFoundException(FOOD_ITEM_NOT_FOUND);
        }
        foodRepository.deleteById(id);
        log.info("Food item with ID={} successfully deleted", id);
    }
}

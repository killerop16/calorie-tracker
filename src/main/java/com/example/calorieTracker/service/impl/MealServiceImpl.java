package com.example.calorieTracker.service.impl;

import com.example.calorieTracker.dto.MealCreateRequestDto;
import com.example.calorieTracker.dto.MealItemRequestDto;
import com.example.calorieTracker.dto.MealResponseDto;
import com.example.calorieTracker.exception.NotFoundException;
import com.example.calorieTracker.mapper.MealMapper;
import com.example.calorieTracker.repository.FoodRepository;
import com.example.calorieTracker.repository.MealRepository;
import com.example.calorieTracker.repository.UserRepository;
import com.example.calorieTracker.service.MealService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.example.calorieTracker.constants.Messages.MEAL_NOT_FOUND;
import static com.example.calorieTracker.constants.Messages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private static final Logger log = LoggerFactory.getLogger(MealServiceImpl.class);

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;
    private final UserRepository userRepository;
    private final FoodRepository foodItemRepository;

    @Transactional
    @Override
    public MealResponseDto addMeal(MealCreateRequestDto request) {
        log.info("Adding meal for user with ID: {}", request.getUserId());
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", request.getUserId());
                    return new NotFoundException(USER_NOT_FOUND);
                });

        var foodItems = foodItemRepository.findAllById(
                request.getFoodItems().stream().map(MealItemRequestDto::getFoodId).toList());

        if (foodItems.size() != request.getFoodItems().size()) {
            log.error("Some food items not found for user with ID: {}", request.getUserId());
            throw new NotFoundException("Some food items not found");
        }

        var meal = mealMapper.mapCreateRequestToMeal(request);
        meal.setUser(user);
        meal.setFoodItems(foodItems);

        var savedMeal = mealRepository.save(meal);
        log.info("Meal successfully added with ID: {}", savedMeal.getId());
        return mealMapper.mapMealToResponse(savedMeal);
    }

    @Override
    public MealResponseDto getMeal(Long id) {
        log.info("Fetching meal with ID: {}", id);
        var meal = mealRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Meal with ID {} not found", id);
                    return new NotFoundException(MEAL_NOT_FOUND);
                });
        return mealMapper.mapMealToResponse(meal);
    }

    @Override
    public List<MealResponseDto> getMealsByUser(Long userId) {
        log.info("Fetching meals for user with ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            log.error("User with ID {} not found", userId);
            throw new NotFoundException(USER_NOT_FOUND
);
        }
        var meals = mealRepository.findByUserId(userId).stream()
                .map(mealMapper::mapMealToResponse)
                .toList();
        log.info("Found {} meals for user with ID: {}", meals.size(), userId);
        return meals;
    }

    @Override
    public void deleteMeal(Long id) {
        log.info("Deleting meal with ID: {}", id);
        if (!mealRepository.existsById(id)) {
            log.error("Meal with ID {} not found", id);
            throw new NotFoundException(MEAL_NOT_FOUND);
        }
        mealRepository.deleteById(id);
        log.info("Meal with ID {} successfully deleted", id);
    }
}

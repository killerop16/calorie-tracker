package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.MealCreateRequestDto;
import com.example.calorieTracker.dto.MealItemRequestDto;
import com.example.calorieTracker.dto.MealResponseDto;
import com.example.calorieTracker.exception.NotFoundException;
import com.example.calorieTracker.mapper.MealMapper;
import com.example.calorieTracker.model.FoodItem;
import com.example.calorieTracker.model.Meal;
import com.example.calorieTracker.model.User;
import com.example.calorieTracker.repository.FoodRepository;
import com.example.calorieTracker.repository.MealRepository;
import com.example.calorieTracker.repository.UserRepository;
import com.example.calorieTracker.service.impl.MealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.calorieTracker.constants.Messages.MEAL_NOT_FOUND;
import static com.example.calorieTracker.constants.Messages.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealServiceImplTest {
    @Mock
    private MealRepository mealRepository;
    @Mock
    private MealMapper mealMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FoodRepository foodRepository;

    private MealServiceImpl mealService;

    @BeforeEach
    void setUp() {
        mealService = new MealServiceImpl(mealRepository, mealMapper, userRepository, foodRepository);
    }

    @Test
    void addMeal_ShouldReturnMealResponse_WhenMealIsCreated() {
        var userId = 1L;
        var mealItemRequest = new MealItemRequestDto(1L, 100);
        var mealCreateRequest = new MealCreateRequestDto(userId, LocalDateTime.now(), List.of(mealItemRequest));

        var user = new User();
        user.setId(userId);

        var foodItem = new FoodItem();
        foodItem.setId(1L);

        var foodItems = List.of(foodItem);
        var meal = new Meal();
        meal.setUser(user);
        meal.setFoodItems(foodItems);

        var mealResponse = new MealResponseDto();
        mealResponse.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(foodRepository.findAllById(any())).thenReturn(foodItems);
        when(mealMapper.mapCreateRequestToMeal(mealCreateRequest)).thenReturn(meal);
        when(mealMapper.mapMealToResponse(meal)).thenReturn(mealResponse);
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        MealResponseDto result = mealService.addMeal(mealCreateRequest);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(mealRepository).save(any(Meal.class));
        verify(mealMapper).mapMealToResponse(meal);
    }


    @Test
    void addMeal_ShouldThrowNotFoundException_WhenUserNotFound() {
        var userId = 1L;
        MealCreateRequestDto request = new MealCreateRequestDto(userId, LocalDateTime.now(), List.of());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> mealService.addMeal(request));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getMeal_ShouldReturnMealResponse_WhenMealExists() {
        var mealId = 1L;
        var meal = new Meal();
        var mealResponseDto = new MealResponseDto();
        when(mealRepository.findById(mealId)).thenReturn(Optional.of(meal));
        when(mealMapper.mapMealToResponse(meal)).thenReturn(mealResponseDto);

        MealResponseDto result = mealService.getMeal(mealId);

        assertNotNull(result);
        verify(mealRepository).findById(mealId);
    }

    @Test
    void getMeal_ShouldThrowNotFoundException_WhenMealNotFound() {
        var mealId = 1L;
        when(mealRepository.findById(mealId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> mealService.getMeal(mealId));
        assertEquals(MEAL_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getMealsByUser_ShouldReturnMealResponses_WhenMealsExist() {
        var userId = 1L;
        var meal = new Meal();
        var mealResponseDto = new MealResponseDto();
        when(userRepository.existsById(userId)).thenReturn(true);
        when(mealRepository.findByUserId(userId)).thenReturn(List.of(meal));
        when(mealMapper.mapMealToResponse(meal)).thenReturn(mealResponseDto);

        List<MealResponseDto> responses = mealService.getMealsByUser(userId);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(mealRepository).findByUserId(userId);
    }

    @Test
    void getMealsByUser_ShouldThrowNotFoundException_WhenUserNotFound() {
        var userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> mealService.getMealsByUser(userId));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void deleteMeal_ShouldDeleteMeal_WhenMealExists() {
        var mealId = 1L;
        when(mealRepository.existsById(mealId)).thenReturn(true);

        mealService.deleteMeal(mealId);

        verify(mealRepository).deleteById(mealId);
    }

    @Test
    void deleteMeal_ShouldThrowNotFoundException_WhenMealNotFound() {
        var mealId = 1L;
        when(mealRepository.existsById(mealId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> mealService.deleteMeal(mealId));
        assertEquals(MEAL_NOT_FOUND, exception.getMessage());
    }
}

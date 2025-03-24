package com.example.calorieTracker.service.impl;

import com.example.calorieTracker.dto.MealResponseDto;
import com.example.calorieTracker.dto.ReportCalCheckResponseDto;
import com.example.calorieTracker.dto.ReportDailyResponseDto;
import com.example.calorieTracker.dto.ReportHistoryResponseDto;
import com.example.calorieTracker.service.MealService;
import com.example.calorieTracker.service.ReportService;
import com.example.calorieTracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final MealService mealService;
    private final UserService userService;

    @Override
    public ReportDailyResponseDto getDailyReport(Long userId) {
        log.info("Requesting daily report for user with ID: {}", userId);
        var today = LocalDate.now();

        var meals = mealService.getMealsByUser(userId);
        var todaysMeals = meals.stream()
                .filter(meal -> meal.getMealTime().toLocalDate().equals(today))
                .toList();
        int totalCalories = todaysMeals.stream()
                .mapToInt(MealResponseDto::getTotalCalories)
                .sum();

        var user = userService.getUser(userId);
        boolean withinCalorieLimit = totalCalories <= user.getDailyCalorieLimit();
        log.info("User {}: {} calories, limit: {}, within limit: {}", userId, totalCalories, user.getDailyCalorieLimit(), withinCalorieLimit);

        return ReportDailyResponseDto.builder()
                .userId(userId)
                .date(today.toString())
                .totalCalories(totalCalories)
                .meals(todaysMeals)
                .withinCalorieLimit(withinCalorieLimit)
                .build();
    }

    @Override
    public ReportCalCheckResponseDto isWithinCalorieLimit(Long userId) {
        log.info("Checking calorie limit for user with ID: {}", userId);
        var user = userService.getUser(userId);
        var meals = mealService.getMealsByUser(userId);

        var today = LocalDate.now();
        var todaysMeals = meals.stream()
                .filter(meal -> meal.getMealTime().toLocalDate().equals(today))
                .toList();
        int totalCalories = todaysMeals.stream()
                .mapToInt(MealResponseDto::getTotalCalories)
                .sum();

        boolean withinCalorieLimit = totalCalories <= user.getDailyCalorieLimit();
        log.info("User {}: {} calories, limit: {}, within limit: {}", userId, totalCalories, user.getDailyCalorieLimit(), withinCalorieLimit);

        return ReportCalCheckResponseDto.builder()
                .userId(userId)
                .withinCalorieLimit(withinCalorieLimit)
                .build();
    }

    @Override
    public ReportHistoryResponseDto getHistory(Long userId) {
        log.info("Requesting meal history for user with ID: {}", userId);
        var meals = mealService.getMealsByUser(userId);

        var startDate = LocalDate.now().minusDays(7);
        var endDate = LocalDateTime.now();

        List<ReportDailyResponseDto> history = new ArrayList<>();

        for (var date = startDate; !date.isAfter(endDate.toLocalDate()); date = date.plusDays(1)) {
            List<MealResponseDto> mealsOnDate = new ArrayList<>();

            for (var meal : meals) {
                if (meal.getMealTime().toLocalDate().equals(date)) {
                    mealsOnDate.add(meal);
                }
            }

            int totalCalories = mealsOnDate.stream()
                    .mapToInt(MealResponseDto::getTotalCalories)
                    .sum();

            var user = userService.getUser(userId);
            boolean withinCalorieLimit = totalCalories <= user.getDailyCalorieLimit();
            log.info("User {}: date {}, {} calories, limit: {}, within limit: {}", userId, date, totalCalories, user.getDailyCalorieLimit(), withinCalorieLimit);

            var dailyReport = ReportDailyResponseDto.builder()
                    .userId(userId)
                    .date(date.toString())
                    .totalCalories(totalCalories)
                    .meals(mealsOnDate)
                    .withinCalorieLimit(withinCalorieLimit)
                    .build();

            history.add(dailyReport);
        }

        return ReportHistoryResponseDto.builder()
                .userId(userId)
                .history(history)
                .build();
    }
}

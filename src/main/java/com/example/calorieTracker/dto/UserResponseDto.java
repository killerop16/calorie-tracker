package com.example.calorieTracker.dto;

import com.example.calorieTracker.enums.Goal;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private int age;
    private double weight;
    private double height;
    private Goal goal;
    private int dailyCalorieLimit;
}

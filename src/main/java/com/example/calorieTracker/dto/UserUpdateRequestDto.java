package com.example.calorieTracker.dto;

import com.example.calorieTracker.annotation.ValidAge;
import com.example.calorieTracker.annotation.ValidHeight;
import com.example.calorieTracker.annotation.ValidWeight;
import com.example.calorieTracker.enums.Goal;
import jakarta.validation.constraints.Email;
import lombok.Data;

import static com.example.calorieTracker.constants.Messages.INVALID_EMAIL_FORMAT;

@Data
public class UserUpdateRequestDto {
    @Email(message = INVALID_EMAIL_FORMAT)
    private String email;

    @ValidAge
    private Integer age;

    @ValidWeight
    private Double weight;

    @ValidHeight
    private Double height;

    private Goal goal;
}

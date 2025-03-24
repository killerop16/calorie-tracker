package com.example.calorieTracker.dto;

import com.example.calorieTracker.annotation.*;
import com.example.calorieTracker.enums.Goal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.example.calorieTracker.constants.Messages.EMAIL_MUST_NOT_BE_EMPTY;
import static com.example.calorieTracker.constants.Messages.INVALID_EMAIL_FORMAT;

@Data
public class UserCreateRequestDto {
    @NotBlankName
    @ValidName
    private String name;

    @NotBlank(message = EMAIL_MUST_NOT_BE_EMPTY)
    @Email(message = INVALID_EMAIL_FORMAT)
    private String email;

    @ValidAge
    private int age;

    @ValidWeight
    private double weight;

    @ValidHeight
    private double height;

    @NotNull(message = "Goal must not be empty")
    private Goal goal; 
}

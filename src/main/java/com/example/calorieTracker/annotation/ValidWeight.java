package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.lang.annotation.*;

@Min(value = 30, message = "Min weight - 30 kg")
@Max(value = 250, message = "Max weight - 250 kg")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidWeight {
}

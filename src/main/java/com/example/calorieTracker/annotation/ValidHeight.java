package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.lang.annotation.*;

@Min(value = 100, message = "Min height - 100 cm")
@Max(value = 250, message = "Max height - 250 cm")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidHeight {
}

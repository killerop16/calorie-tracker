package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Positive;
import java.lang.annotation.*;

@Positive(message = "ID must be a positive number")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PositiveId {
}
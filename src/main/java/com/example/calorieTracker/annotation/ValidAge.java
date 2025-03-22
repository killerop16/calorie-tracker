package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.lang.annotation.*;

@Min(value = 18, message = "Min age - 18 years")
@Max(value = 99, message = "Max age - 99 years")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidAge {
}

package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Positive;
import java.lang.annotation.*;

@Positive(message = "Name must not be empty")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotBlankName {
}

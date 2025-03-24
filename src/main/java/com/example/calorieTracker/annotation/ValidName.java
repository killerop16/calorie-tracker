package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Size;
import java.lang.annotation.*;

@Size(min = 2, max = 50, message = "Name length must must be 2-50 symbols")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidName {
}
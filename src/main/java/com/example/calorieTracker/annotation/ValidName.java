package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Size;
import java.lang.annotation.*;

import static com.example.calorieTracker.constants.Messages.NAME_LENGTH_MUST_MUST_BE_2_50_SYMBOLS;

@Size(min = 2, max = 50, message = NAME_LENGTH_MUST_MUST_BE_2_50_SYMBOLS)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidName {
}

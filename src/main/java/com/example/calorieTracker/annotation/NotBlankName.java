package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Positive;
import java.lang.annotation.*;

import static com.example.calorieTracker.constants.Messages.NAME_MUST_NOT_BE_EMPTY;

@Positive(message = NAME_MUST_NOT_BE_EMPTY)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotBlankName {
}

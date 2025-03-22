package com.example.calorieTracker.annotation;

import jakarta.validation.constraints.Positive;
import java.lang.annotation.*;

import static com.example.calorieTracker.constants.Messages.ID_MUST_BE_A_POSITIVE_NUMBER;

@Positive(message = ID_MUST_BE_A_POSITIVE_NUMBER)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PositiveId {
}

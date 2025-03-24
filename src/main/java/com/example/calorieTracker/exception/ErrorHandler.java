package com.example.calorieTracker.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError fullLimitHandle(ConstraintViolationException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflictHandle(IllegalArgumentException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError psqlValidationHandle(SQLException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundHandle(NotFoundException exception) {
        log.error("404 {}", exception.getMessage(), exception);
        return new ApiError(
                HttpStatus.NOT_FOUND,
                "The required object was not found.",
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        log.error("400 {}", exception.getMessage(), exception);

        Throwable cause = exception.getCause();
        if (cause instanceof InvalidFormatException invalidFormat) {

            String fieldName = "unknown";
            if (!invalidFormat.getPath().isEmpty()) {
                fieldName = invalidFormat.getPath().get(0).getFieldName();
            }

            if (invalidFormat.getTargetType().isEnum()) {
                Object[] constants = invalidFormat.getTargetType().getEnumConstants();
                String allowedValues = java.util.Arrays.stream(constants)
                        .map(Object::toString)
                        .collect(java.util.stream.Collectors.joining(", "));

                String userMessage = String.format(
                        "Invalid value for field '%s'. Allowed values: [%s]",
                        fieldName, allowedValues
                );

                return new ApiError(
                        HttpStatus.BAD_REQUEST,
                        "Invalid enum value",
                        userMessage,
                        LocalDateTime.now()
                );
            }
        }

        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON request",
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError mainExceptionHandle(Throwable throwable) {
        log.error("500 {}", throwable.getMessage(), throwable);
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Oops, something went wrong...",
                throwable.getMessage(),
                LocalDateTime.now()
        );
    }

}

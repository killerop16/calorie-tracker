package com.example.calorieTracker.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

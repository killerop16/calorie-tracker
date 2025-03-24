package com.example.calorieTracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportCalCheckResponseDto {
    private Long userId;
    private boolean withinCalorieLimit;
}

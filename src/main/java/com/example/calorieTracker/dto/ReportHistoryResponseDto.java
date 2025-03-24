package com.example.calorieTracker.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ReportHistoryResponseDto {
    private Long userId;
    private List<ReportDailyResponseDto> history;
}

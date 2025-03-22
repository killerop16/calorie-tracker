package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.ReportCalCheckResponseDto;
import com.example.calorieTracker.dto.ReportDailyResponseDto;
import com.example.calorieTracker.dto.ReportHistoryResponseDto;

public interface ReportService {
    ReportDailyResponseDto getDailyReport(Long userId);
    ReportCalCheckResponseDto isWithinCalorieLimit(Long userId);
    ReportHistoryResponseDto getHistory(Long userId);
}

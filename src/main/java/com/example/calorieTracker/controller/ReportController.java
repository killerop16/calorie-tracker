package com.example.calorieTracker.controller;

import com.example.calorieTracker.annotation.PositiveUserId;
import com.example.calorieTracker.dto.ReportCalCheckResponseDto;
import com.example.calorieTracker.dto.ReportDailyResponseDto;
import com.example.calorieTracker.dto.ReportHistoryResponseDto;
import com.example.calorieTracker.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Validated
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/daily")
    public ResponseEntity<ReportDailyResponseDto> getDailyReport(@RequestParam @PositiveUserId Long userId) {
        return ResponseEntity.ok(reportService.getDailyReport(userId));
    }

    @GetMapping("/summary")
    public ResponseEntity<ReportCalCheckResponseDto> checkCalorieLimit(@RequestParam @PositiveUserId Long userId) {
        return ResponseEntity.ok(reportService.isWithinCalorieLimit(userId));
    }

    @GetMapping("/history")
    public ResponseEntity<ReportHistoryResponseDto> getHistory(@RequestParam @PositiveUserId Long userId) {
        return ResponseEntity.ok(reportService.getHistory(userId));
    }
}

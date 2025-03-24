package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.MealResponseDto;
import com.example.calorieTracker.dto.ReportCalCheckResponseDto;
import com.example.calorieTracker.dto.ReportDailyResponseDto;
import com.example.calorieTracker.dto.UserResponseDto;
import com.example.calorieTracker.exception.NotFoundException;
import com.example.calorieTracker.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.calorieTracker.constants.Messages.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    private MealService mealService;
    @Mock
    private UserService userService;

    @InjectMocks
    private ReportServiceImpl reportService;

    private UserResponseDto mockUser;
    private MealResponseDto mockMealResponseDto;
    private List<MealResponseDto> mockMeals;

    @BeforeEach
    void setUp() {
        mockUser = new UserResponseDto();
        mockUser.setId(1L);
        mockUser.setName("John Doe");
        mockUser.setEmail("john.doe@example.com");
        mockUser.setDailyCalorieLimit(2000);

        mockMealResponseDto = new MealResponseDto();
        mockMealResponseDto.setMealTime(LocalDateTime.now());
        mockMealResponseDto.setTotalCalories(500);

        mockMeals = List.of(mockMealResponseDto);
    }

    @Test
    void getDailyReport_ShouldReturnCorrectResponse() {
        var userId = 1L;
        when(mealService.getMealsByUser(userId)).thenReturn(mockMeals);
        when(userService.getUser(userId)).thenReturn(mockUser);

        ReportDailyResponseDto response = reportService.getDailyReport(userId);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(LocalDate.now().toString(), response.getDate());
        assertEquals(500, response.getTotalCalories());
        assertTrue(response.isWithinCalorieLimit());
    }

    @Test
    void isWithinCalorieLimit_ShouldReturnCorrectResponse() {
        var userId = 1L;
        when(mealService.getMealsByUser(userId)).thenReturn(mockMeals);
        when(userService.getUser(userId)).thenReturn(mockUser);

        ReportCalCheckResponseDto response = reportService.isWithinCalorieLimit(userId);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertTrue(response.isWithinCalorieLimit());
    }

    @Test
    void getUser_ShouldThrowNotFoundException_WhenUserNotFound() {
        var invalidUserId = 999L;
        when(userService.getUser(invalidUserId)).thenThrow(new NotFoundException(USER_NOT_FOUND));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> reportService.getDailyReport(invalidUserId));

        assertEquals(USER_NOT_FOUND, exception.getMessage());
    }
}

package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.FoodCreateRequestDto;
import com.example.calorieTracker.dto.FoodResponseDto;
import com.example.calorieTracker.dto.FoodUpdateRequestDto;
import com.example.calorieTracker.exception.NotFoundException;
import com.example.calorieTracker.mapper.FoodMapper;
import com.example.calorieTracker.model.FoodItem;
import com.example.calorieTracker.repository.FoodRepository;
import com.example.calorieTracker.service.impl.FoodServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodServiceImplTest {
    @Mock
    private FoodRepository foodRepository;
    @Mock
    private FoodMapper foodMapper;

    @InjectMocks
    private FoodServiceImpl foodService;

    private FoodItem foodItem;
    private FoodCreateRequestDto foodCreateRequestDto;
    private FoodUpdateRequestDto foodUpdateRequestDto;
    private FoodResponseDto foodResponseDto;

    @BeforeEach
    void setUp() {
        foodItem = new FoodItem();
        foodItem.setId(1L);
        foodItem.setName("Apple");

        foodCreateRequestDto = new FoodCreateRequestDto();
        foodCreateRequestDto.setName("Apple");

        foodUpdateRequestDto = new FoodUpdateRequestDto();
        foodUpdateRequestDto.setName("Updated Apple");

        foodResponseDto = new FoodResponseDto();
        foodResponseDto.setName("Apple");
    }

    @Test
    void createFood_Success() {
        when(foodRepository.findByName(foodCreateRequestDto.getName())).thenReturn(Optional.empty());
        when(foodMapper.mapCreateRequestToFoodItem(foodCreateRequestDto)).thenReturn(foodItem);
        when(foodRepository.save(foodItem)).thenReturn(foodItem);
        when(foodMapper.mapFoodItemToResponse(foodItem)).thenReturn(foodResponseDto);

        FoodResponseDto result = foodService.createFood(foodCreateRequestDto);

        assertNotNull(result);
        assertEquals(foodResponseDto.getName(), result.getName());
        verify(foodRepository).save(foodItem);
    }

    @Test
    void createFood_ThrowsExceptionWhenFoodExists() {
        when(foodRepository.findByName(foodCreateRequestDto.getName())).thenReturn(Optional.of(foodItem));

        assertThrows(IllegalArgumentException.class, () -> foodService.createFood(foodCreateRequestDto));
        verify(foodRepository, never()).save(any());
    }

    @Test
    void getFoodById_Success() {
        when(foodRepository.findById(1L)).thenReturn(Optional.of(foodItem));
        when(foodMapper.mapFoodItemToResponse(foodItem)).thenReturn(foodResponseDto);

        FoodResponseDto result = foodService.getFoodById(1L);

        assertNotNull(result);
        verify(foodRepository).findById(1L);
    }

    @Test
    void getFoodById_ThrowsNotFoundException() {
        when(foodRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> foodService.getFoodById(1L));
    }

    @Test
    void getAllFoods_Success() {
        when(foodRepository.findAll()).thenReturn(List.of(foodItem));
        when(foodMapper.mapFoodItemToResponse(foodItem)).thenReturn(foodResponseDto);

        List<FoodResponseDto> result = foodService.getAllFoods();

        assertEquals(1, result.size());
        verify(foodRepository).findAll();
    }

    @Test
    void updateFood_Success() {
        when(foodRepository.findById(1L)).thenReturn(Optional.of(foodItem));
        doNothing().when(foodMapper).updateFoodItemFromRequest(foodUpdateRequestDto, foodItem);
        when(foodRepository.save(foodItem)).thenReturn(foodItem);
        when(foodMapper.mapFoodItemToResponse(foodItem)).thenReturn(foodResponseDto);

        FoodResponseDto result = foodService.updateFood(1L, foodUpdateRequestDto);

        assertNotNull(result);
        assertEquals(foodResponseDto.getName(), result.getName());
        verify(foodRepository).save(foodItem);
    }

    @Test
    void updateFood_ThrowsNotFoundException() {
        when(foodRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> foodService.updateFood(1L, foodUpdateRequestDto));
    }

    @Test
    void deleteFood_Success() {
        when(foodRepository.existsById(1L)).thenReturn(true);
        doNothing().when(foodRepository).deleteById(1L);

        foodService.deleteFood(1L);

        verify(foodRepository).deleteById(1L);
    }

    @Test
    void deleteFood_ThrowsNotFoundException() {
        when(foodRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> foodService.deleteFood(1L));
    }
}

package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.UserCreateRequestDto;
import com.example.calorieTracker.dto.UserResponseDto;
import com.example.calorieTracker.dto.UserUpdateRequestDto;
import com.example.calorieTracker.enums.Goal;
import com.example.calorieTracker.exception.NotFoundException;
import com.example.calorieTracker.mapper.UserMapper;
import com.example.calorieTracker.model.User;
import com.example.calorieTracker.repository.UserRepository;
import com.example.calorieTracker.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponseDto userResponseDto;
    private UserCreateRequestDto userCreateRequestDto;
    private UserUpdateRequestDto userUpdateRequestDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .age(25)
                .weight(70.0)
                .height(175.0)
                .goal(Goal.MAINTENANCE)
                .dailyCalorieLimit(2000)
                .build();

        userResponseDto = new UserResponseDto();
        userResponseDto.setId(1L);
        userResponseDto.setName("John Doe");
        userResponseDto.setEmail("john@example.com");
        userResponseDto.setAge(25);
        userResponseDto.setWeight(70.0);
        userResponseDto.setHeight(175.0);
        userResponseDto.setGoal(Goal.MAINTENANCE);
        userResponseDto.setDailyCalorieLimit(2000);

        userCreateRequestDto = new UserCreateRequestDto();
        userCreateRequestDto.setName("John Doe");
        userCreateRequestDto.setEmail("john@example.com");
        userCreateRequestDto.setAge(25);
        userCreateRequestDto.setWeight(70.0);
        userCreateRequestDto.setHeight(175.0);
        userCreateRequestDto.setGoal(Goal.MAINTENANCE);

        userUpdateRequestDto = new UserUpdateRequestDto();
        userUpdateRequestDto.setEmail("new_email@example.com");
        userUpdateRequestDto.setAge(30);
        userUpdateRequestDto.setWeight(75.0);
        userUpdateRequestDto.setHeight(180.0);
        userUpdateRequestDto.setGoal(Goal.GAIN);
    }

    @Test
    void createUser_Success() {
        when(userMapper.mapUserCreateRequestToUser(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.mapUserToUserResponse(any())).thenReturn(userResponseDto);

        UserResponseDto result = userService.createUser(userCreateRequestDto);

        assertNotNull(result);
        assertEquals(userResponseDto.getId(), result.getId());
        assertEquals(userResponseDto.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void getUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.mapUserToUserResponse(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals(userResponseDto.getId(), result.getId());
        assertEquals(userResponseDto.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void getAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.mapUserToUserResponse(user)).thenReturn(userResponseDto);

        List<UserResponseDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userResponseDto.getId(), result.get(0).getId());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.mapUserToUserResponse(any())).thenReturn(userResponseDto);

        UserResponseDto result = userService.updateUser(1L, userUpdateRequestDto);

        assertNotNull(result);
        assertEquals(userResponseDto.getId(), result.getId());
        assertEquals(userResponseDto.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void updateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(1L, userUpdateRequestDto));
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(1L));
    }
}

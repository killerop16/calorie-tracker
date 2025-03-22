package com.example.calorieTracker.service;

import com.example.calorieTracker.dto.UserCreateRequestDto;
import com.example.calorieTracker.dto.UserResponseDto;
import com.example.calorieTracker.dto.UserUpdateRequestDto;
import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserCreateRequestDto userDto);
    UserResponseDto getUser(Long id);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, UserUpdateRequestDto userDto);
    void deleteUser(Long id);
}

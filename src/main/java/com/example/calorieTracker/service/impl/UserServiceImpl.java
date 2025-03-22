package com.example.calorieTracker.service.impl;

import com.example.calorieTracker.dto.UserCreateRequestDto;
import com.example.calorieTracker.dto.UserResponseDto;
import com.example.calorieTracker.dto.UserUpdateRequestDto;
import com.example.calorieTracker.exception.NotFoundException;
import com.example.calorieTracker.mapper.UserMapper;
import com.example.calorieTracker.model.User;
import com.example.calorieTracker.repository.UserRepository;
import com.example.calorieTracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserCreateRequestDto userDto) {
        log.info("Creating new user with data: {}", userDto);
        var user = userMapper.mapUserCreateRequestToUser(userDto);
        user.setDailyCalorieLimit(calculateDailyCalories(user));
        var savedUser = userRepository.save(user);
        log.info("User with ID {} successfully created", savedUser.getId());
        return userMapper.mapUserToUserResponse(savedUser);
    }

    @Override
    public UserResponseDto getUser(Long id) {
        log.info("Fetching user with ID: {}", id);
        var user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", id);
                    return new NotFoundException("User not found with ID: " + id);
                });
        log.info("User with ID {} found", user.getId());
        return userMapper.mapUserToUserResponse(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        log.info("Fetching list of all users");
        List<UserResponseDto> users = userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserResponse)
                .collect(Collectors.toList());
        log.info("Found {} users", users.size());
        return users;
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto userDto) {
        log.info("Updating user with ID: {}", id);
        var user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", id);
                    return new NotFoundException("User not found with ID: " + id);
                });
        userMapper.updateUserFromRequest(userDto, user);

        if (userDto.getWeight() != null || userDto.getHeight() != null || userDto.getGoal() != null) {
            user.setDailyCalorieLimit(calculateDailyCalories(user));
            log.info("Updated data used to calculate daily calorie limit for user with ID: {}", id);
        }

        user = userRepository.save(user);
        log.info("User data with ID {} successfully updated", user.getId());
        return userMapper.mapUserToUserResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.error("User with ID {} not found", id);
            throw new NotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        log.info("User with ID {} successfully deleted", id);
    }

    private int calculateDailyCalories(User user) {
        double bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        log.debug("BMR for user with ID {}: {}", user.getId(), bmr);
        return switch (user.getGoal()) {
            case LOSS -> {
                log.debug("Goal: Weight loss");
                yield (int) (bmr * 0.8);
            }
            case MAINTENANCE -> {
                log.debug("Goal: Maintenance");
                yield (int) bmr;
            }
            case GAIN -> {
                log.debug("Goal: Weight gain");
                yield (int) (bmr * 1.2);
            }
        };
    }
}

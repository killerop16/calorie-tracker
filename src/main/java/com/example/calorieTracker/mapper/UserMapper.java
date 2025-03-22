package com.example.calorieTracker.mapper;

import com.example.calorieTracker.dto.UserCreateRequestDto;
import com.example.calorieTracker.dto.UserResponseDto;
import com.example.calorieTracker.dto.UserUpdateRequestDto;
import com.example.calorieTracker.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dailyCalorieLimit", ignore = true)
    User mapUserCreateRequestToUser(UserCreateRequestDto request);

    @Mapping(target = "id", source = "id")
    UserResponseDto mapUserToUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "dailyCalorieLimit", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UserUpdateRequestDto request, @MappingTarget User user);

}

package com.auth.authRouting.mapper;

import com.auth.authRouting.dao.response.UserDto;
import com.auth.authRouting.model.User;

public class UserMapper {
    public static UserDto getDto(User user){
     return UserDto.builder()
             .id(user.getId())
             .firstName(user.getFirstName())
             .lastName(user.getLastName())
             .email(user.getEmail())
             .role(user.getRole())
             .build();
    }
}

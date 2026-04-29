package com.hardik.backend.mapper;

import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;
import com.hardik.backend.model.UserEntity;

public class UserMapper {

    public static UserEntity ToUserEntity(UserRequestDto req, Role role) {
        return UserEntity.builder()
                .name(req.getUsername())
                .email(req.getEmail())
                .role(role)
                .build();
    }
}

package com.hardik.backend.service;

import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;

public interface UserService {

    Boolean registerUser(UserRequestDto userRequestDto, Role role);
}

package com.hardik.backend.service.impl;

import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;
import com.hardik.backend.mapper.UserMapper;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.repository.UserRepository;
import com.hardik.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Boolean registerUser(UserRequestDto userRequestDto, Role role) {
         Optional<UserEntity> alreadyExist =userRepository.findByEmail(userRequestDto.getEmail());
         if(alreadyExist.isPresent()){
             throw new RuntimeException("User already exist");
         }
        UserEntity user =UserMapper.ToUserEntity(userRequestDto , role);
        UserEntity isSaved = userRepository.save(user);
       return isSaved.getId() != null;
    }
}

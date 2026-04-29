package com.hardik.backend.service.impl;

import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;
import com.hardik.backend.mapper.UserMapper;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.repository.UserRepository;
import com.hardik.backend.service.EmailService;
import com.hardik.backend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Boolean registerUser(UserRequestDto userRequestDto, Role role) {
        Optional<UserEntity> alreadyExist = userRepository.findByEmail(userRequestDto.getEmail());
        if (alreadyExist.isPresent()) {
            throw new RuntimeException("User already exist");
        }
        // Generate 6 digit Otp
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        emailService.sendWelcomeWithOtp(userRequestDto.getEmail(), userRequestDto.getUsername(), otp);
        UserEntity user = UserMapper.ToUserEntity(userRequestDto, role);
        user.setPassword(passwordEncoder.encode(otp));
        UserEntity isSaved = userRepository.save(user);
        return isSaved.getId() != null;
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        UserEntity existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user does not exist by this email : " + email));
        if (!passwordEncoder.matches(oldPassword, existUser.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        existUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existUser);
    }

    @Transactional
    @Override
    public void sendResetOtp(String email) {
        UserEntity existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user does not exist by this email : " + email));
        // Generate 6 digit Otp
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        existUser.setResetOtp(otp);
        emailService.resetOtp(email, otp);
        existUser.setResetOtpExpireAt(System.currentTimeMillis() + (5 * 60 * 1000));
        userRepository.save(existUser);
    }

    @Transactional
    @Override
    public void verifyResetOtpAndChangePassword(String email, String otp, String newPassword) {
        UserEntity existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user does not exist by this email : " + email));
        if (existUser.getResetOtp() == null) {
            throw new RuntimeException("No OTP request found");
        }
        if (existUser.getResetOtpExpireAt() < System.currentTimeMillis()) {
            throw new RuntimeException("Otp expired");
        }
        if (!otp.equals(existUser.getResetOtp())) {
            throw new BadCredentialsException("Invalid OTP");
        }
        existUser.setPassword(passwordEncoder.encode(newPassword));
        existUser.setResetOtpExpireAt(0L);
        existUser.setResetOtp(null);
        userRepository.save(existUser);
    }
}

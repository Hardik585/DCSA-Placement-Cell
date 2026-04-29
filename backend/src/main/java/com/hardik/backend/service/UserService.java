package com.hardik.backend.service;

import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;

public interface UserService {

    Boolean registerUser(UserRequestDto userRequestDto, Role role);

    public void changePassword(String email, String oldPassword, String newPassword);
    public void sendResetOtp(String email);
    public void verifyResetOtpAndChangePassword(String email, String otp, String newPassword);

}

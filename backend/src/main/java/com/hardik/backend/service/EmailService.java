package com.hardik.backend.service;

public interface EmailService {

    public void sendWelcomeWithOtp(String toEmail, String name , String otp );

    public void resetOtp(String toEmail, String otp);
}

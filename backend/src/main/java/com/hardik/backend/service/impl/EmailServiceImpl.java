package com.hardik.backend.service.impl;

import com.hardik.backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendWelcomeWithOtp(String toEmail, String name, String otp) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Welcome to DCSA Placement Cell - Email Verification");
            String content =
                    "Dear " + name + ",\n\n"
                            + "Welcome to the DCSA Placement Cell Portal.\n\n"
                            + "Thank you for registering with the Department of Computer Science and Applications.\n\n"
                            + "Your One-Time Password (OTP) for account verification is:\n\n"
                            + otp + "\n\n"
                            + "Please use this OTP to verify your account.\n\n"
                            + "If you did not register on our portal, please ignore this email.\n\n"
                            + "Regards,\n"
                            + "DCSA Placement Cell\n"
                            + "Department of Computer Science and Applications";
            helper.setText(content);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Email sending failed");
            e.printStackTrace();
        }
    }

    public void resetOtp(String toEmail, String otp) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject("Password Reset CODE ");
            mimeMessageHelper.setText("Please use this code to reset the password for your account "
                    + toEmail + "\n Here is your code:\n\n" + otp
                    + "Thanks,\n\n"
                    + "Department of Computer Science and Applications");
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("Email sending failed");
            e.printStackTrace();
        }
    }
}

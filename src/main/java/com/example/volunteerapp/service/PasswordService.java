package com.example.volunteerapp.service;

import com.example.volunteerapp.dto.ForgotPasswordRequest;
import com.example.volunteerapp.dto.ResetPasswordRequest;
import com.example.volunteerapp.entity.PasswordResetToken;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.repository.PasswordResetTokenRepository;
import com.example.volunteerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class PasswordService {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordResetTokenRepository tokenRepo;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder encoder;

    @Transactional
    public void createPasswordResetToken(ForgotPasswordRequest req) {
        UserInfo user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // remove existing tokens
        tokenRepo.deleteByUser(user);

        String token = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plus(1, ChronoUnit.HOURS);
        PasswordResetToken prt = new PasswordResetToken(token, user, expiry);
        tokenRepo.save(prt);

        String resetUrl = "http://localhost:4200/reset-password?token=" + token;
        String text = "To reset your password, click the link below:\n" + resetUrl;
        try {
            emailService.sendSimpleMessage(user.getEmail(), "Password Reset Request", text);
        } catch (Exception e) {
            // log error; you could inform user that email failed
            System.err.println("Error sending reset email: " + e.getMessage());
        }


    }

    @Transactional
    public void resetPassword(ResetPasswordRequest req) {
        PasswordResetToken prt = tokenRepo.findByToken(req.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (prt.getExpiryDate().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        UserInfo user = prt.getUser();
        user.setPassword(encoder.encode(req.getNewPassword()));
        userRepo.save(user);

        tokenRepo.delete(prt);
    }
}

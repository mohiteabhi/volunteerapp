package com.example.volunteerapp.controller;

import com.example.volunteerapp.dto.*;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.repository.UserRepository;
import com.example.volunteerapp.security.JwtUtils;

import com.example.volunteerapp.service.PasswordService;
import com.example.volunteerapp.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private PasswordService passwordService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        UserInfo user = new UserInfo();
        user.setFullName(req.getFullName());
        user.setAge(req.getAge());
        user.setEmail(req.getEmail());
        user.setAddress(req.getAddress());
        user.setContactNo(req.getContactNo());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest req) {
        Authentication auth;
        try {
                    auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Invalid email or password");
        }


        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        String token = jwtUtils.generateJwtToken(req.getEmail(), userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(
                token,
                userDetails.getFullName(),
                userDetails.getUsername()
        ));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
        passwordService.createPasswordResetToken(req);
        return ResponseEntity.ok(new MessageResponse("Password reset email sent"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        passwordService.resetPassword(req);
        return ResponseEntity.ok(new MessageResponse("Password reset successful"));
    }
}

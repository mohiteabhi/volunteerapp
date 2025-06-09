package com.example.volunteerapp.controller;

import com.example.volunteerapp.dto.*;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.repository.UserRepository;
import com.example.volunteerapp.security.JwtUtils;

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
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Invalid email or password");
        }

        String token = jwtUtils.generateJwtToken(req.getEmail());
        UserInfo user = userRepo.findByEmail(req.getEmail()).get();
        return ResponseEntity.ok(new JwtResponse(token, user.getFullName(), user.getEmail()));
    }
}

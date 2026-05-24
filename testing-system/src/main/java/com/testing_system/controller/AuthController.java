package com.testing_system.controller;

import com.testing_system.dto.AuthRequest;
import com.testing_system.dto.AuthResponse;
import com.testing_system.model.User;
import com.testing_system.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(hashPassword(request.getPassword()))) {
                return ResponseEntity.ok(new AuthResponse(user.getId(), user.getUsername(), user.getRole()));
            }
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPassword(request.getPassword()));
        user.setRole(request.getRole());
        
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new AuthResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRole()));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}

package com.tourtravel.service.impl;

import com.tourtravel.dto.request.SignupRequest;

import com.tourtravel.service.JwtService;

import com.tourtravel.dto.request.LoginRequest;
import com.tourtravel.dto.request.ForgotPasswordRequest;
import com.tourtravel.dto.request.ResetPasswordRequest;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.response.LoginResponse;
import com.tourtravel.entity.User;
import com.tourtravel.repository.UserRepository;
import com.tourtravel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tourtravel.enums.Role;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public ApiResponse registerUser(SignupRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // Validate if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed: Email already registered - {}", request.getEmail());
            throw new RuntimeException("Email already registered");
        }

        // Create new User entity from SignupRequest
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();

        // Save user to database
        userRepository.save(user);
        log.info("User registered successfully with email: {}", request.getEmail());

        // Return success response
        return ApiResponse.builder()
                .success(true)
                .message("User registered successfully")
                .build();
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed: User not found with email - {}", request.getEmail());
                    return new RuntimeException("Invalid email or password");
                });

        // Verify password using BCrypt
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed: Invalid password for email - {}", request.getEmail());
            throw new RuntimeException("Invalid email or password");
        }

        // Check if account is enabled
        if (!user.getEnabled()) {
            log.warn("Login failed: Account is disabled for email - {}", request.getEmail());
            throw new RuntimeException("Account is disabled");
        }

        log.info("User login successful for email: {}", request.getEmail());

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        // Return user details with JWT token in response
        return LoginResponse.builder()
                .success(true)
                .message("Login successful")
                .token(token)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public ApiResponse forgotPassword(ForgotPasswordRequest request) {
        log.info("Forgot password request for email: {}", request.getEmail());

        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            log.info("Password reset request accepted for email: {}", request.getEmail());
            return ApiResponse.builder()
                    .success(true)
                    .message("Password reset request accepted")
                    .build();
        }

        log.warn("Forgot password failed: User not found with email - {}", request.getEmail());
        return ApiResponse.builder()
                .success(false)
                .message("User not found")
                .build();
    }

    @Override
    public ApiResponse resetPassword(ResetPasswordRequest request) {
        log.info("Reset password request for email: {}", request.getEmail());

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Reset password failed: User not found with email - {}", request.getEmail());
                    return new RuntimeException("User not found");
                });

        // Encrypt new password using BCryptPasswordEncoder
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedPassword);

        // Save user to database
        userRepository.save(user);
        log.info("Password reset successfully for email: {}", request.getEmail());

        // Return success response
        return ApiResponse.builder()
                .success(true)
                .message("Password reset successfully")
                .build();
    }
}

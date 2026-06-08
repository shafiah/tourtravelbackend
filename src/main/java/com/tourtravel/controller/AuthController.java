package com.tourtravel.controller;

import com.tourtravel.dto.request.SignupRequest;
import com.tourtravel.dto.request.LoginRequest;
import com.tourtravel.dto.request.ForgotPasswordRequest;
import com.tourtravel.dto.request.ResetPasswordRequest;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.response.LoginResponse;
import com.tourtravel.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user with signup credentials.
     *
     * @param request the SignupRequest containing registration details
     * @return ResponseEntity with ApiResponse and HTTP 201 CREATED status
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        log.info("Signup request received for email: {}", request.getEmail());

        try {
            ApiResponse response = userService.registerUser(request);
            log.info("User registration successful for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            log.error("User registration failed: {}", e.getMessage());
            ApiResponse errorResponse = ApiResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Authenticate user with email and password.
     *
     * @param request the LoginRequest containing credentials
     * @return ResponseEntity with LoginResponse and HTTP 200 OK status
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        log.info("Login request received for email: {}", request.getEmail());

        try {
            LoginResponse response = userService.loginUser(request);
            log.info("User login successful for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            log.error("User login failed: {}", e.getMessage());
            LoginResponse errorResponse = LoginResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    /**
     * Request a password reset for the given email.
     *
     * @param request the ForgotPasswordRequest containing email
     * @return ResponseEntity with ApiResponse and HTTP 200 OK status
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Forgot password request received for email: {}", request.getEmail());

        try {
            ApiResponse response = userService.forgotPassword(request);
            log.info("Forgot password request processed for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            log.error("Forgot password request failed: {}", e.getMessage());
            ApiResponse errorResponse = ApiResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Reset the password for the given email with a new password.
     *
     * @param request the ResetPasswordRequest containing email and new password
     * @return ResponseEntity with ApiResponse and HTTP 200 OK status
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Reset password request received for email: {}", request.getEmail());

        try {
            ApiResponse response = userService.resetPassword(request);
            log.info("Password reset successful for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            log.error("Password reset failed: {}", e.getMessage());
            ApiResponse errorResponse = ApiResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

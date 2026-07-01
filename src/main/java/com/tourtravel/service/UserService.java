package com.tourtravel.service;

import com.tourtravel.dto.request.SignupRequest;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.request.LoginRequest;
import com.tourtravel.dto.request.ForgotPasswordRequest;
import com.tourtravel.dto.request.ResetPasswordRequest;
import com.tourtravel.dto.response.LoginResponse;
import com.tourtravel.dto.request.UpdateProfileRequest;
import com.tourtravel.dto.request.ChangePasswordRequest;
import com.tourtravel.dto.response.ProfileResponse;

public interface UserService {

    /**
     * Register a new user with the provided signup information.
     *
     * @param request the SignupRequest containing user registration details
     * @return ApiResponse indicating success or failure of registration
     * @throws RuntimeException if email is already registered
     */
    ApiResponse registerUser(SignupRequest request);
    
    /**
     * Authenticate a user with email and password.
     *
     * @param request the LoginRequest containing email and password
     * @return LoginResponse containing user details on successful authentication
     * @throws RuntimeException if email not found, password invalid, or account disabled
     */
    LoginResponse loginUser(LoginRequest request);

    /**
     * Request a password reset for the given email.
     *
     * @param request the ForgotPasswordRequest containing email
     * @return ApiResponse indicating if password reset request was accepted
     */
    ApiResponse forgotPassword(ForgotPasswordRequest request);

    /**
     * Reset the password for the given email with a new password.
     *
     * @param request the ResetPasswordRequest containing email and new password
     * @return ApiResponse indicating success or failure of password reset
     * @throws RuntimeException if user not found
     */
    ApiResponse resetPassword(ResetPasswordRequest request);
    
    /**
     * Get logged-in user's profile.
     *
     * @param email logged-in user's email
     * @return profile details
     */
    ProfileResponse getProfile(String email);

    /**
     * Update logged-in user's profile.
     *
     * @param email logged-in user's email
     * @param request profile update request
     * @return success response
     */
    ApiResponse updateProfile(String email, UpdateProfileRequest request);

    /**
     * Change logged-in user's password.
     *
     * @param email logged-in user's email
     * @param request password change request
     * @return success response
     */
    ApiResponse changePassword(String email, ChangePasswordRequest request);
}

package com.tourtravel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tourtravel.dto.request.ChangePasswordRequest;
import com.tourtravel.dto.request.UpdateProfileRequest;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.response.ProfileResponse;
import com.tourtravel.service.UserService;

import jakarta.validation.Valid;

/**
 * REST Controller for User APIs.
 * Accessible by USER and ADMIN.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
	    this.userService = userService;
	}

	/**
	 * Get logged-in user's profile.
	 */
	@GetMapping("/profile")
	public ResponseEntity<ProfileResponse> getProfile(
	        Authentication authentication) {

	    return ResponseEntity.ok(
	            userService.getProfile(authentication.getName()));
	}

	/**
	 * Update logged-in user's profile.
	 */
	@PutMapping("/profile")
	public ResponseEntity<ApiResponse> updateProfile(
	        Authentication authentication,
	        @Valid @RequestBody UpdateProfileRequest request) {

	    return ResponseEntity.ok(
	            userService.updateProfile(authentication.getName(), request));
	}

	/**
	 * Change logged-in user's password.
	 */
	@PutMapping("/change-password")
	public ResponseEntity<ApiResponse> changePassword(
	        Authentication authentication,
	        @Valid @RequestBody ChangePasswordRequest request) {

	    return ResponseEntity.ok(
	            userService.changePassword(authentication.getName(), request));
	}
}
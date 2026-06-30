package com.tourtravel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * REST Controller for User APIs.
 * Accessible by USER and ADMIN.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * Protected User Profile API.
     *
     * @param authentication Spring Security Authentication
     * @return Success response
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> profile(Authentication authentication) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("success", true);
        response.put("message", "Welcome User");
        response.put("email", authentication.getName());

        return ResponseEntity.ok(response);
    }
}
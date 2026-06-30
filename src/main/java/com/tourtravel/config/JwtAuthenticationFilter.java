package com.tourtravel.config;

import com.tourtravel.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

import com.tourtravel.entity.User;
import com.tourtravel.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

/**
 * JWT Authentication Filter that validates JWT tokens on every request.
 * Validates JWT token from Authorization header and sets Spring Security context.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * Filter method that processes JWT token from request.
     * Validates the token and sets Spring Security context.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain FilterChain
     * @throws ServletException if servlet error occurs
     * @throws IOException if IO error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) 
            throws ServletException, IOException {

        try {
            // Extract JWT token from Authorization header
            String token = extractTokenFromRequest(request);

            if (token != null && jwtService.validateToken(token)) {
                // Extract email from token
                String email = jwtService.extractEmail(token);
                log.debug("JWT token validated for email: {}", email);

                // Create authentication token
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                        );
                // Set authentication in Spring Security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.debug("Authentication set in SecurityContext for email: {}", email);
            } else if (token != null) {
                log.warn("Invalid or expired JWT token received");
            }

        } catch (Exception e) {
            log.error("Error processing JWT token: {}", e.getMessage());
            // Continue with filter chain even if JWT processing fails
            // The security filter will handle unauthorized access
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from Authorization header.
     * Expected format: "Bearer <token>"
     *
     * @param request HttpServletRequest
     * @return JWT token or null if not found
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }
}

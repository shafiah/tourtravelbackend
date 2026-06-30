package com.tourtravel.service.impl;

import com.tourtravel.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Implementation of JwtService for JWT token operations.
 * Uses HS256 algorithm for token signing.
 * Compatible with JJWT 0.12.3
 */
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Generate a JWT token for the given email.
     * Token expiry: 24 hours (86400000 milliseconds)
     * Algorithm: HS256
     *
     * @param email the email address to encode in the token
     * @return the generated JWT token
     */
    @Override
    public String generateToken(String email) {
        log.info("Generating JWT token for email: {}", email);

        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            String token = Jwts.builder()
                    .subject(email)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(key)
                    .compact();

            log.info("JWT token generated successfully for email: {}", email);
            return token;

        } catch (Exception e) {
            log.error("Error generating JWT token for email: {}", email, e);
            throw new RuntimeException("Failed to generate JWT token: " + e.getMessage());
        }
    }

    /**
     * Validate the given JWT token.
     * Checks signature, expiration, and format.
     *
     * @param token the JWT token to validate
     * @return true if token is valid, false otherwise
     */
    @Override
    public boolean validateToken(String token) {
        log.debug("Validating JWT token");

        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            JwtParser parser = Jwts.parser()
                    .verifyWith(key)
                    .build();

            parser.parseSignedClaims(token);

            log.debug("JWT token is valid");
            return true;

        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.error("Invalid JWT token signature: {}", e.getMessage());
            return false;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Error validating JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extract email from the given JWT token.
     * The email is stored as the subject of the token.
     *
     * @param token the JWT token
     * @return the email address encoded in the token
     * @throws RuntimeException if token extraction fails or token is invalid
     */
    @Override
    public String extractEmail(String token) {
        log.debug("Extracting email from JWT token");

        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            JwtParser parser = Jwts.parser()
                    .verifyWith(key)
                    .build();

            Claims claims = parser.parseSignedClaims(token)
                    .getPayload();

            String email = claims.getSubject();
            log.debug("Email extracted from JWT token: {}", email);
            return email;

        } catch (Exception e) {
            log.error("Error extracting email from JWT token: {}", e.getMessage());
            throw new RuntimeException("Failed to extract email from JWT token: " + e.getMessage());
        }
    }
}

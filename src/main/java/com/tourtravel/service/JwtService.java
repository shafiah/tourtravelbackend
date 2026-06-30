package com.tourtravel.service;

/**
 * Service interface for JWT token operations.
 */
public interface JwtService {

    /**
     * Generate a JWT token for the given email.
     *
     * @param email the email address to encode in the token
     * @return the generated JWT token
     * @throws RuntimeException if token generation fails
     */
    String generateToken(String email);

    /**
     * Validate the given JWT token.
     *
     * @param token the JWT token to validate
     * @return true if token is valid, false otherwise
     */
    boolean validateToken(String token);

    /**
     * Extract email from the given JWT token.
     *
     * @param token the JWT token
     * @return the email address encoded in the token
     * @throws RuntimeException if token extraction fails or token is invalid
     */
    String extractEmail(String token);
}

package com.tourtravel.service;

/**
 * Service interface for OTP operations.
 */
public interface OtpService {

    /**
     * Generate and send OTP to the specified email.
     *
     * @param email the recipient's email address
     * @throws RuntimeException if OTP generation or sending fails
     */
    void sendOtp(String email);

    /**
     * Verify the OTP for the given email.
     *
     * @param email the email address
     * @param otp the OTP code to verify
     * @throws RuntimeException if verification fails
     */
    void verifyOtp(String email, String otp);
}

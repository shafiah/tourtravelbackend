package com.tourtravel.service;

/**
 * Service interface for sending emails.
 */
public interface EmailService {

    /**
     * Send an OTP email to the specified recipient with the provided OTP code.
     *
     * @param toEmail the recipient's email address
     * @param otp the one-time password to be sent
     * @throws RuntimeException if email sending fails
     */
    void sendOtpEmail(String toEmail, String otp);
}
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
    
    /**
     * Send booking confirmation email to customer.
     *
     * @param customerEmail customer email
     * @param customerName customer name
     * @param destination destination
     */
    void sendBookingConfirmationToCustomer(
            String customerEmail,
            String customerName,
            String destination);

    /**
     * Notify admin about new booking.
     *
     * @param customerName customer name
     * @param customerEmail customer email
     * @param destination destination
     */
    void sendBookingNotificationToAdmin(
            String customerName,
            String customerEmail,
            String destination);

    /**
     * Send contact enquiry confirmation to customer.
     *
     * @param customerEmail customer email
     * @param customerName customer name
     */
    void sendContactConfirmationToCustomer(
            String customerEmail,
            String customerName);

    /**
     * Notify admin about new contact enquiry.
     *
     * @param customerName customer name
     * @param customerEmail customer email
     */
    void sendContactNotificationToAdmin(
            String customerName,
            String customerEmail);
}
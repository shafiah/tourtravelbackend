package com.tourtravel.service.impl;

import com.tourtravel.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of EmailService for sending emails using JavaMailSender.
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.properties.from-email:noreply@tourtravel.com}")
    private String fromEmail;

    @Value("${spring.mail.properties.from-name:Tour Travel Team}")
    private String fromName;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        log.info("Sending OTP email to: {}", toEmail);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Tour Travel - Email Verification OTP");
            message.setText(buildEmailBody(otp));

            javaMailSender.send(message);
            log.info("OTP email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }

    /**
     * Build the email body with the OTP.
     *
     * @param otp the one-time password
     * @return the formatted email body
     */
    private String buildEmailBody(String otp) {
        return "Dear User,\n\n" +
                "Your OTP for email verification is:\n\n" +
                otp + "\n\n" +
                "This OTP is valid for 10 minutes.\n\n" +
                "Thank you,\n" +
                "Tour Travel Team";
    }
}

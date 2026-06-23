package com.tourtravel.service.impl;

import com.tourtravel.entity.OtpEntity;
import com.tourtravel.repository.OtpRepository;
import com.tourtravel.service.EmailService;
import com.tourtravel.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@Transactional
public class OtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 10;

    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public OtpServiceImpl(OtpRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    /**
     * Generate a 6-digit OTP and send it to the specified email.
     *
     * @param email the recipient's email address
     * @throws RuntimeException if email is invalid or OTP sending fails
     */
    @Override
    public void sendOtp(String email) {
        log.info("Generating OTP for email: {}", email);

        // Validate email format
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            log.error("Invalid email provided: {}", email);
            throw new RuntimeException("Invalid email address");
        }

        try {
            // Generate 6-digit OTP
            String otp = generateOtp();
            log.debug("Generated OTP: {}", otp);

            // Calculate expiry time (10 minutes from now)
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);

            // Create and save OTP entity
            OtpEntity otpEntity = OtpEntity.builder()
                    .email(email)
                    .otp(otp)
                    .expiryTime(expiryTime)
                    .verified(false)
                    .build();

            otpRepository.save(otpEntity);
            log.info("OTP saved to database for email: {}", email);

            // Send OTP via email
            emailService.sendOtpEmail(email, otp);
            log.info("OTP sent successfully to email: {}", email);

        } catch (Exception e) {
            log.error("Error sending OTP for email: {}", email, e);
            throw new RuntimeException("Failed to send OTP: " + e.getMessage());
        }
    }

    /**
     * Verify the OTP for the given email and mark it as verified.
     *
     * @param email the email address
     * @param otp the OTP code to verify
     * @throws RuntimeException if email, OTP is invalid, or OTP has expired
     */
    @Override
    public void verifyOtp(String email, String otp) {
        log.info("Verifying OTP for email: {}", email);

        // Validate email
        if (email == null || email.trim().isEmpty()) {
            log.error("Invalid email provided for OTP verification");
            throw new RuntimeException("Email is required");
        }

        // Validate OTP
        if (otp == null || otp.trim().isEmpty() || otp.length() != OTP_LENGTH) {
            log.error("Invalid OTP format for email: {}", email);
            throw new RuntimeException("OTP must be a 6-digit code");
        }

        try {
            // Find valid (not expired, not verified) OTP with current timestamp
            Optional<OtpEntity> otpRecord = otpRepository.findValidOtpByEmailAndOtp(
                    email, 
                    otp, 
                    LocalDateTime.now()
            );

            if (otpRecord.isEmpty()) {
                log.warn("No valid OTP found for email: {} with provided OTP", email);
                throw new RuntimeException("Invalid or expired OTP");
            }

            // Mark OTP as verified
            OtpEntity otpEntity = otpRecord.get();
            otpEntity.setVerified(true);
            otpRepository.save(otpEntity);

            log.info("OTP verified successfully for email: {}", email);

        } catch (RuntimeException e) {
            log.error("OTP verification failed for email: {}", email, e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during OTP verification for email: {}", email, e);
            throw new RuntimeException("OTP verification failed: " + e.getMessage());
        }
    }

    /**
     * Generate a random 6-digit OTP.
     *
     * @return the generated OTP as a string
     */
    private String generateOtp() {
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        return String.valueOf(otpNumber);
    }
}

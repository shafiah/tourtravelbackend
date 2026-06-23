package com.tourtravel.controller;

import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller for email/SMTP configuration testing.
 * This controller is used only for testing purposes and should be removed
 * or disabled in production environments.
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestEmailController {

    private final EmailService emailService;

    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Test endpoint to send a sample OTP email to the configured Gmail address.
     * This endpoint is used for testing SMTP configuration.
     *
     * @return ResponseEntity with ApiResponse containing success message
     */
    @GetMapping("/send-email")
    public ResponseEntity<ApiResponse> sendTestEmail() {
        log.info("Test email endpoint called");

        try {
            // Send OTP email with test value "123456" to configured Gmail address
            String testEmail = "support.abititradingzone@gmail.com";
            String testOtp = "123456";

            emailService.sendOtpEmail(testEmail, testOtp);
            log.info("Test email sent successfully to: {}", testEmail);

            ApiResponse response = ApiResponse.builder()
                    .success(true)
                    .message("Test email sent successfully to " + testEmail)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Failed to send test email: {}", e.getMessage(), e);

            ApiResponse errorResponse = ApiResponse.builder()
                    .success(false)
                    .message("Failed to send test email: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

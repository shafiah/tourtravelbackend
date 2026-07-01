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
    
    @Value("${app.admin.email}")
    private String adminEmail;

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
    
    @Override
    public void sendBookingConfirmationToCustomer(
            String customerEmail,
            String customerName,
            String destination) {

        log.info("Sending booking confirmation email to {}", customerEmail);

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(customerEmail);
            message.setSubject("Booking Request Received");

            message.setText(
                    "Dear " + customerName + ",\n\n" +
                    "Thank you for choosing Tour Travel.\n\n" +
                    "We have successfully received your booking request.\n\n" +
                    "Destination : " + destination + "\n\n" +
                    "Our team will contact you shortly.\n\n" +
                    "Regards,\n" +
                    fromName
            );

            javaMailSender.send(message);

            log.info("Booking confirmation email sent.");

        } catch (Exception e) {

            log.error("Failed to send booking confirmation email", e);

        }
    }

    @Override
    public void sendBookingNotificationToAdmin(
            String customerName,
            String customerEmail,
            String destination) {

        log.info("Sending booking notification to admin");

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(adminEmail);
            message.setSubject("New Booking Received");

            message.setText(
                    "A new booking has been received.\n\n" +
                    "Customer : " + customerName + "\n" +
                    "Email : " + customerEmail + "\n" +
                    "Destination : " + destination
            );

            javaMailSender.send(message);

            log.info("Admin booking notification sent.");

        } catch (Exception e) {

            log.error("Failed to send admin booking notification", e);

        }
    }

    @Override
    public void sendContactConfirmationToCustomer(
            String customerEmail,
            String customerName) {

        log.info("Sending contact confirmation email");

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(customerEmail);
            message.setSubject("We have received your enquiry");

            message.setText(
                    "Dear " + customerName + ",\n\n" +
                    "Thank you for contacting Tour Travel.\n\n" +
                    "We have received your enquiry successfully.\n\n" +
                    "Our team will get back to you shortly.\n\n" +
                    "Regards,\n" +
                    fromName
            );

            javaMailSender.send(message);

            log.info("Customer enquiry email sent.");

        } catch (Exception e) {

            log.error("Failed to send enquiry confirmation", e);

        }
    }

    @Override
    public void sendContactNotificationToAdmin(
            String customerName,
            String customerEmail) {

        log.info("Sending contact notification to admin");

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(adminEmail);
            message.setSubject("New Contact Enquiry");

            message.setText(
                    "A new contact enquiry has been received.\n\n" +
                    "Customer : " + customerName + "\n" +
                    "Email : " + customerEmail
            );

            javaMailSender.send(message);

            log.info("Admin enquiry notification sent.");

        } catch (Exception e) {

            log.error("Failed to send admin enquiry notification", e);

        }
    }
}

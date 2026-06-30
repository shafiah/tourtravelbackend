package com.tourtravel.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Request DTO for Contact Us form.
 * Contains validation rules for user enquiry.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateContactRequest {

    /**
     * Customer Full Name
     */
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100,
            message = "Full name must be between 2 and 100 characters")
    private String fullName;

    /**
     * Customer Email
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Size(max = 100,
            message = "Email must not exceed 100 characters")
    private String email;

    /**
     * Customer Phone Number
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    private String phoneNumber;

    /**
     * Preferred Destination
     */
    @NotBlank(message = "Preferred destination is required")
    @Size(max = 100,
            message = "Preferred destination must not exceed 100 characters")
    private String preferredDestination;

    /**
     * User Message
     */
    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 1000,
            message = "Message must be between 10 and 1000 characters")
    private String message;

}
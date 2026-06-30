package com.tourtravel.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Request DTO for creating a travel booking.
 * Contains validation rules for all booking fields.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateBookingRequest {

    /**
     * Destination selected by customer.
     */
    @NotBlank(message = "Destination is required")
    @Size(max = 100, message = "Destination must not exceed 100 characters")
    private String destination;

    /**
     * Departure date.
     */
    @NotNull(message = "Departure date is required")
    @FutureOrPresent(message = "Departure date cannot be in the past")
    private LocalDate departureDate;

    /**
     * Departure city.
     */
    @NotBlank(message = "Departure city is required")
    @Size(max = 100, message = "Departure city must not exceed 100 characters")
    private String departureCity;

    /**
     * Customer full name.
     */
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    /**
     * Customer phone number.
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    private String phoneNumber;

    /**
     * Customer email.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    /**
     * Number of adults.
     * Allowed: 1 to 10
     */
    @NotNull(message = "Adults count is required")
    @Min(value = 1, message = "Minimum 1 adult is required")
    @Max(value = 10, message = "Maximum 10 adults are allowed")
    private Integer adults;

    /**
     * Number of children.
     * Allowed: 0 to 10
     */
    @NotNull(message = "Children count is required")
    @Min(value = 0, message = "Children cannot be negative")
    @Max(value = 10, message = "Maximum 10 children are allowed")
    private Integer children;

    /**
     * Number of infants.
     * Allowed: 0 to 10
     */
    @NotNull(message = "Infants count is required")
    @Min(value = 0, message = "Infants cannot be negative")
    @Max(value = 10, message = "Maximum 10 infants are allowed")
    private Integer infants;

}
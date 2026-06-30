package com.tourtravel.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for Booking API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private String destination;

    private LocalDate departureDate;

    private String departureCity;

    private String fullName;

    private String phoneNumber;

    private String email;

    private Integer adults;

    private Integer children;

    private Integer infants;

    private String bookingStatus;

    private LocalDateTime createdAt;
}
package com.tourtravel.dto.response;

import com.tourtravel.enums.BookingStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for Admin Booking Response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminBookingResponse {

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

    private BookingStatus bookingStatus;

    private LocalDateTime createdAt;

}
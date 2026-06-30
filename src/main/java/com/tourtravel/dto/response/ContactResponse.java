package com.tourtravel.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Response DTO for Contact Us API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String preferredDestination;

    private String message;

    private String status;

    private LocalDateTime createdAt;
}
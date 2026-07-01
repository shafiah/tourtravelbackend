package com.tourtravel.dto.response;

import com.tourtravel.enums.ContactStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for Admin Contact Response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminContactResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String preferredDestination;

    private String message;

    private ContactStatus status;

    private LocalDateTime createdAt;

}
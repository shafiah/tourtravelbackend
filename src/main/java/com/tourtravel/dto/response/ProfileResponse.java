package com.tourtravel.dto.response;

import com.tourtravel.enums.Role;

import lombok.*;

/**
 * Response DTO for logged-in user's profile.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProfileResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;

}
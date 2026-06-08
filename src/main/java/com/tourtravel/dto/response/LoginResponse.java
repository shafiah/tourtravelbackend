package com.tourtravel.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginResponse {

    private boolean success;
    private String message;
    private String firstName;
    private String lastName;
    private String email;
}

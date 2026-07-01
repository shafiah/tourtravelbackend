package com.tourtravel.dto.request;

import com.tourtravel.enums.ContactStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Request DTO for updating contact enquiry status.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateContactStatusRequest {

    @NotNull(message = "Status is required")
    private ContactStatus status;

}
package com.tourtravel.dto.request;

import com.tourtravel.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Request DTO for updating booking status.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateBookingStatusRequest {

    @NotNull(message = "Booking status is required")
    private BookingStatus bookingStatus;

}
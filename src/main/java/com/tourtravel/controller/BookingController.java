package com.tourtravel.controller;

import com.tourtravel.dto.request.CreateBookingRequest;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.response.BookingResponse;
import com.tourtravel.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling booking related APIs.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Create a new travel booking.
     *
     * @param request booking request
     * @return success response
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {

        BookingResponse bookingResponse = bookingService.createBooking(request);

        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Booking request submitted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
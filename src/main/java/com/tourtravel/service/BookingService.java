package com.tourtravel.service;

import com.tourtravel.dto.request.CreateBookingRequest;
import com.tourtravel.dto.response.BookingResponse;

/**
 * Service interface for Booking operations.
 * Handles business logic related to travel package bookings.
 */
public interface BookingService {

    /**
     * Creates a new booking.
     *
     * @param request booking request DTO
     * @return booking response DTO
     */
    BookingResponse createBooking(CreateBookingRequest request);

}
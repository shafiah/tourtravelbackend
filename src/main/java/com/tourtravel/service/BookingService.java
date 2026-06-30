package com.tourtravel.service;

import com.tourtravel.dto.request.CreateBookingRequest;
import com.tourtravel.dto.request.UpdateBookingStatusRequest;
import com.tourtravel.dto.response.AdminBookingResponse;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.response.BookingResponse;

import java.util.List;

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

    /**
     * Get all bookings.
     *
     * @return list of bookings
     */
    List<AdminBookingResponse> getAllBookings();

    /**
     * Get booking by ID.
     *
     * @param id booking ID
     * @return booking details
     */
    AdminBookingResponse getBookingById(Long id);

    /**
     * Update booking status.
     *
     * @param id booking ID
     * @param request status update request
     * @return success response
     */
    ApiResponse updateBookingStatus(Long id, UpdateBookingStatusRequest request);

    /**
     * Delete booking.
     *
     * @param id booking ID
     * @return success response
     */
    ApiResponse deleteBooking(Long id);

}
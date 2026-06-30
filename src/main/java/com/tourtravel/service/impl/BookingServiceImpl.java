package com.tourtravel.service.impl;

import com.tourtravel.dto.request.CreateBookingRequest;
import com.tourtravel.dto.response.BookingResponse;
import com.tourtravel.entity.Booking;
import com.tourtravel.repository.BookingRepository;
import com.tourtravel.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of BookingService.
 * Handles booking creation and database operations.
 */
@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Creates a new booking and saves it into database.
     *
     * @param request booking request
     * @return booking response
     */
    @Override
    public BookingResponse createBooking(CreateBookingRequest request) {

        log.info("Creating booking for email: {}", request.getEmail());

        // Convert Request DTO -> Entity
        Booking booking = Booking.builder()
                .destination(request.getDestination())
                .departureDate(request.getDepartureDate())
                .departureCity(request.getDepartureCity())
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .adults(request.getAdults())
                .children(request.getChildren())
                .infants(request.getInfants())
                .bookingStatus("PENDING")
                .build();

        // Save into database
        Booking savedBooking = bookingRepository.save(booking);

        log.info("Booking created successfully with ID: {}", savedBooking.getId());

        // Convert Entity -> Response DTO
        return BookingResponse.builder()
                .id(savedBooking.getId())
                .destination(savedBooking.getDestination())
                .departureDate(savedBooking.getDepartureDate())
                .departureCity(savedBooking.getDepartureCity())
                .fullName(savedBooking.getFullName())
                .phoneNumber(savedBooking.getPhoneNumber())
                .email(savedBooking.getEmail())
                .adults(savedBooking.getAdults())
                .children(savedBooking.getChildren())
                .infants(savedBooking.getInfants())
                .bookingStatus(savedBooking.getBookingStatus())
                .createdAt(savedBooking.getCreatedAt())
                .build();
    }
}
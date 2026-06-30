package com.tourtravel.service.impl;

import com.tourtravel.dto.request.CreateBookingRequest;
import com.tourtravel.dto.response.BookingResponse;
import com.tourtravel.entity.Booking;
import com.tourtravel.repository.BookingRepository;
import com.tourtravel.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tourtravel.enums.BookingStatus;

import com.tourtravel.dto.request.UpdateBookingStatusRequest;
import com.tourtravel.dto.response.AdminBookingResponse;
import com.tourtravel.dto.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

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
                .bookingStatus(BookingStatus.PENDING)
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
    @Override
    public List<AdminBookingResponse> getAllBookings() {

        log.info("Fetching all bookings");

        return bookingRepository.findAll()
                .stream()
                .map(booking -> AdminBookingResponse.builder()
                        .id(booking.getId())
                        .destination(booking.getDestination())
                        .departureDate(booking.getDepartureDate())
                        .departureCity(booking.getDepartureCity())
                        .fullName(booking.getFullName())
                        .phoneNumber(booking.getPhoneNumber())
                        .email(booking.getEmail())
                        .adults(booking.getAdults())
                        .children(booking.getChildren())
                        .infants(booking.getInfants())
                        .bookingStatus(booking.getBookingStatus())
                        .createdAt(booking.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public AdminBookingResponse getBookingById(Long id) {

        log.info("Fetching booking with ID: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        return AdminBookingResponse.builder()
                .id(booking.getId())
                .destination(booking.getDestination())
                .departureDate(booking.getDepartureDate())
                .departureCity(booking.getDepartureCity())
                .fullName(booking.getFullName())
                .phoneNumber(booking.getPhoneNumber())
                .email(booking.getEmail())
                .adults(booking.getAdults())
                .children(booking.getChildren())
                .infants(booking.getInfants())
                .bookingStatus(booking.getBookingStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }

    @Override
    public ApiResponse updateBookingStatus(Long id, UpdateBookingStatusRequest request) {

        log.info("Updating booking status for ID: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with ID: " + id));

        booking.setBookingStatus(request.getBookingStatus());

        bookingRepository.save(booking);

        log.info("Booking status updated successfully for ID: {}", id);

        return ApiResponse.builder()
                .success(true)
                .message("Booking status updated successfully")
                .build();
    }

    @Override
    public ApiResponse deleteBooking(Long id) {

        log.info("Deleting booking with ID: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with ID: " + id));

        bookingRepository.delete(booking);

        log.info("Booking deleted successfully with ID: {}", id);

        return ApiResponse.builder()
                .success(true)
                .message("Booking deleted successfully")
                .build();
    }
}
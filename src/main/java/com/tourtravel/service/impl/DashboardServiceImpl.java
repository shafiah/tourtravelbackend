package com.tourtravel.service.impl;

import com.tourtravel.dto.response.DashboardResponse;
import com.tourtravel.repository.BookingRepository;
import com.tourtravel.repository.ContactRepository;
import com.tourtravel.repository.UserRepository;
import com.tourtravel.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of DashboardService.
 * Provides dashboard statistics for Admin.
 */
@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ContactRepository contactRepository;

    public DashboardServiceImpl(UserRepository userRepository,
                                BookingRepository bookingRepository,
                                ContactRepository contactRepository) {

        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public DashboardResponse getDashboardStatistics() {

        log.info("Fetching admin dashboard statistics");

        return DashboardResponse.builder()

                // Users
                .totalUsers(userRepository.count())

                // Bookings
                .totalBookings(bookingRepository.count())
                .pendingBookings(
                        bookingRepository.countByBookingStatus(
                                com.tourtravel.enums.BookingStatus.PENDING))
                .confirmedBookings(
                        bookingRepository.countByBookingStatus(
                                com.tourtravel.enums.BookingStatus.CONFIRMED))
                .completedBookings(
                        bookingRepository.countByBookingStatus(
                                com.tourtravel.enums.BookingStatus.COMPLETED))
                .cancelledBookings(
                        bookingRepository.countByBookingStatus(
                                com.tourtravel.enums.BookingStatus.CANCELLED))

                // Contacts
                .totalContacts(contactRepository.count())
                .newContacts(
                        contactRepository.countByStatus(
                                com.tourtravel.enums.ContactStatus.NEW))
                .readContacts(
                        contactRepository.countByStatus(
                                com.tourtravel.enums.ContactStatus.READ))
                .respondedContacts(
                        contactRepository.countByStatus(
                                com.tourtravel.enums.ContactStatus.RESPONDED))
                .closedContacts(
                        contactRepository.countByStatus(
                                com.tourtravel.enums.ContactStatus.CLOSED))

                .build();
    }
}
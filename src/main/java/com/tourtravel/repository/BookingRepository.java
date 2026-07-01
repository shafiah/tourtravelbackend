package com.tourtravel.repository;

import com.tourtravel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Booking entity.
 * Provides CRUD operations for booking records.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	/**
	 * Count bookings by status.
	 *
	 * @param bookingStatus booking status
	 * @return total bookings
	 */
	Long countByBookingStatus(com.tourtravel.enums.BookingStatus bookingStatus);

}